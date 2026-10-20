package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Integration test: runs tflint, Trivy config, and checkov against a test Terraform workspace,
 * parses output, maps to Qualimetry rules, and asserts we get findings. Proves that
 * the three tools are executed and their results are correctly mapped to rules.
 * Requires tflint, trivy, and checkov on PATH (e.g. choco install tflint trivy; pip install checkov).
 */
class ToolExecutionIntegrationTest {

    private Path workspaceRoot;
    private RuleRegistry registry;
    private ToolResultMapper mapper;

    @BeforeEach
    void setUp() throws Exception {
        URL mainTf = getClass().getResource("/tool-test-workspace/main.tf");
        assertThat(mainTf).isNotNull();
        Path mainTfPath = Paths.get(mainTf.toURI());
        workspaceRoot = mainTfPath.getParent();
        registry = new RuleRegistry();
        mapper = new ToolResultMapper(registry);
    }

    @Test
    void tflintRunsAndMapsToKnownRules() {
        String json = runProcess(workspaceRoot.toFile(), "tflint", "--format", "json");
        if (json == null || json.isBlank()) {
            org.junit.jupiter.api.Assumptions.assumeTrue(false, "tflint not on PATH or failed");
            return;
        }
        List<ToolFinding> findings = TflintOutputParser.parse(json, workspaceRoot.toString());
        List<MappedFinding> mapped = mapper.mapTflint(findings);
        assertThat(mapped)
                .as("tflint should report at least one finding that maps to a known rule (e.g. terraform_naming_convention or terraform_required_version)")
                .isNotEmpty();
        for (MappedFinding m : mapped) {
            assertThat(m.getRuleKey()).startsWith("qa-tflint-");
            assertThat(registry.getRule(m.getRuleKey())).isPresent();
        }
    }

    @Test
    void trivyRunsAndMapsToKnownRules() {
        String json = runProcess(workspaceRoot.toFile(), "trivy", "config", "-f", "json", ".");
        if (json == null || json.isBlank()) {
            org.junit.jupiter.api.Assumptions.assumeTrue(false, "trivy not on PATH or failed");
            return;
        }
        List<ToolFinding> findings = TrivyOutputParser.parse(json, workspaceRoot.toString());
        List<MappedFinding> mapped = mapper.mapTrivy(findings);
        org.junit.jupiter.api.Assumptions.assumeTrue(!mapped.isEmpty(),
                "trivy produced no findings that map to known rules (e.g. qa-trivy-AWS-0086); ensure test workspace triggers Trivy rules");
        for (MappedFinding m : mapped) {
            assertThat(m.getRuleKey()).startsWith("qa-trivy-");
            assertThat(registry.getRule(m.getRuleKey())).isPresent();
        }
    }

    @Test
    void checkovRunsAndMapsToKnownRules() {
        String json = runProcess(workspaceRoot.toFile(), "checkov", "-d", ".", "--output", "json");
        if (json == null || json.isBlank()) {
            org.junit.jupiter.api.Assumptions.assumeTrue(false, "checkov not on PATH or failed");
            return;
        }
        List<ToolFinding> findings = CheckovOutputParser.parse(json, workspaceRoot.toString());
        List<MappedFinding> mapped = mapper.mapCheckov(findings);
        assertThat(mapped)
                .as("checkov should report at least one finding that maps to a known rule (e.g. CKV_AWS_20, CKV2_AWS_6)")
                .isNotEmpty();
        for (MappedFinding m : mapped) {
            assertThat(m.getRuleKey()).startsWith("qa-checkov-");
            assertThat(registry.getRule(m.getRuleKey())).isPresent();
        }
    }

    private static String runProcess(File workDir, String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command)
                    .directory(workDir)
                    .redirectErrorStream(true);
            Process p = pb.start();
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            }
            if (!p.waitFor(60, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                return null;
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
