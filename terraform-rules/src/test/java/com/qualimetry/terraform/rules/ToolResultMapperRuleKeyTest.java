package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Parameterized test: for every rule key (tflint, Trivy, Checkov) in the registry, builds minimal
 * synthetic tool output, parses with the correct parser, runs ToolResultMapper, and asserts
 * that exactly one MappedFinding is produced with the expected rule key.
 * <p>
 * Each invocation records its result (PASS/FAIL) into a shared list. After all invocations,
 * {@link #generateEvidenceReport()} runs and produces the evidence report from these actual
 * test results (same pattern as the Gherkin and Ansible plugins: evidence report is legitimately
 * produced from the unit tests).
 */
@Order(1)
class ToolResultMapperRuleKeyTest {

    private static final RuleRegistry REGISTRY = new RuleRegistry();
    private static final ToolResultMapper MAPPER = new ToolResultMapper(REGISTRY);

    /** Collected results from each parameterized test run; used to generate the evidence report. */
    private static final List<EvidenceReportGenerator.RuleEvidenceResult> EVIDENCE_RESULTS =
            Collections.synchronizedList(new ArrayList<>());

    static Stream<Arguments> allRuleKeys() {
        return REGISTRY.getAllRules().stream()
                .filter(r -> "tflint".equals(r.getSource()) || "trivy".equals(r.getSource()) || "checkov".equals(r.getSource()))
                .map(r -> Arguments.of(r.getId(), r.getSource(), toolRuleIdFrom(r.getId(), r.getSource())));
    }

    private static String toolRuleIdFrom(String ruleKey, String source) {
        if ("tflint".equals(source)) return ruleKey.substring("qa-tflint-".length());
        if ("trivy".equals(source)) return ruleKey.substring("qa-trivy-".length());
        if ("checkov".equals(source)) return ruleKey.substring("qa-checkov-".length());
        throw new IllegalArgumentException(source);
    }

    private static String syntheticTflintJson(String toolRuleId) {
        return "{\"issues\":[{\"rule\":{\"name\":\"" + escapeJson(toolRuleId) + "\"},"
                + "\"message\":\"synthetic\",\"range\":{\"filename\":\"x.tf\",\"start\":{\"line\":1}}}]}";
    }

    private static String syntheticTrivyJson(String toolRuleId) {
        return "{\"Results\":[{\"Target\":\"x.tf\",\"Misconfigurations\":["
                + "{\"ID\":\"" + escapeJson(toolRuleId) + "\",\"Message\":\"synthetic\",\"CauseMetadata\":{\"StartLine\":1}}"
                + "]}]}";
    }

    private static String syntheticCheckovJson(String toolRuleId) {
        return "{\"results\":{\"failed_checks\":[{"
                + "\"check_id\":\"" + escapeJson(toolRuleId) + "\","
                + "\"file_path\":\"x.tf\",\"file_abs_path\":\"/project/x.tf\",\"file_line_range\":[1,2],\"message\":\"synthetic\"}"
                + "]}}";
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allRuleKeys")
    void mapsSyntheticFindingToRuleKey(String ruleKey, String source, String toolRuleId) {
        try {
            String json;
            List<ToolFinding> findings;
            List<MappedFinding> mapped;
            if ("tflint".equals(source)) {
                json = syntheticTflintJson(toolRuleId);
                findings = TflintOutputParser.parse(json, null);
                mapped = MAPPER.mapTflint(findings);
            } else if ("trivy".equals(source)) {
                json = syntheticTrivyJson(toolRuleId);
                findings = TrivyOutputParser.parse(json, null);
                mapped = MAPPER.mapTrivy(findings);
            } else {
                json = syntheticCheckovJson(toolRuleId);
                findings = CheckovOutputParser.parse(json, "/project");
                mapped = MAPPER.mapCheckov(findings);
            }

            assertThat(findings).hasSize(1);
            assertThat(findings.get(0).getToolRuleId()).isEqualTo(toolRuleId);
            assertThat(mapped).hasSize(1);
            assertThat(mapped.get(0).getRuleKey()).isEqualTo(ruleKey);
            // Checkov parser normalizes with project base path; we pass "/project" so "/project/x.tf" -> "x.tf"
            assertThat(mapped.get(0).getFile()).isEqualTo("x.tf");
            assertThat(mapped.get(0).getLine()).isEqualTo(1);
            assertThat(mapped.get(0).getMessage()).isEqualTo("synthetic");
            assertThat(mapped.get(0).getSeverity()).isNotNull();
            assertThat(mapped.get(0).getType()).isNotNull();

            EVIDENCE_RESULTS.add(EvidenceReportGenerator.RuleEvidenceResult.pass(ruleKey));
        } catch (Throwable t) {
            EVIDENCE_RESULTS.add(EvidenceReportGenerator.RuleEvidenceResult.fail(ruleKey, t.getMessage()));
            throw t;
        }
    }

    @AfterAll
    static void generateEvidenceReport() throws Exception {
        assertThat(REGISTRY.getAllRules().size()).as(
                "Rule registry should contain all 766 rules. Run 'npm run content:generate' and rebuild if you see fewer.")
                .isGreaterThanOrEqualTo(766);

        Path reportDir = Path.of("target", "evidence-report");
        EvidenceReportGenerator.generate(reportDir, EVIDENCE_RESULTS);

        long failures = EVIDENCE_RESULTS.stream().filter(r -> "FAIL".equals(r.status())).count();
        assertThat(failures).as("Evidence report should have zero failures. Check target/evidence-report/").isZero();

        Path zipFile = zipEvidenceReport(reportDir);
        System.out.println("Evidence pack: " + zipFile.toAbsolutePath());
    }

    private static Path zipEvidenceReport(Path evidenceDir) throws IOException {
        Path projectRoot = evidenceDir.toAbsolutePath()
                .getParent()   // target
                .getParent()   // terraform-rules
                .getParent();  // project root
        Path zipFile = projectRoot.resolve("terraform-evidence-report.zip");
        Files.deleteIfExists(zipFile);
        try (OutputStream fos = Files.newOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.setLevel(9);
            Path base = evidenceDir;
            Files.walkFileTree(base, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    String entryName = "evidence-report/"
                            + base.relativize(file).toString().replace('\\', '/');
                    zos.putNextEntry(new ZipEntry(entryName));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    if (!dir.equals(base)) {
                        String entryName = "evidence-report/"
                                + base.relativize(dir).toString().replace('\\', '/') + "/";
                        zos.putNextEntry(new ZipEntry(entryName));
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        return zipFile;
    }
}
