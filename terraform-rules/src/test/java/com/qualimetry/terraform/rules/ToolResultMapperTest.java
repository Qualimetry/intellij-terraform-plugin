package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

class ToolResultMapperTest {

    private static String resource(String name) {
        try (InputStream in = ToolResultMapperTest.class.getResourceAsStream("/" + name)) {
            return in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    @Test
    void mapsTflintFindingsToRuleKeys() {
        RuleRegistry registry = new RuleRegistry();
        ToolResultMapper mapper = new ToolResultMapper(registry);
        List<ToolFinding> findings = List.of(
                new ToolFinding("main.tf", 1, "terraform_naming_convention", "msg"));
        List<MappedFinding> mapped = mapper.mapTflint(findings);
        assertThat(mapped).hasSize(1);
        assertThat(mapped.get(0).getRuleKey()).isEqualTo("qa-tflint-terraform_naming_convention");
        assertThat(mapped.get(0).getSeverity()).isEqualTo("MAJOR");
    }

    @Test
    void mapsTrivyFindings() {
        RuleRegistry registry = new RuleRegistry();
        ToolResultMapper mapper = new ToolResultMapper(registry);
        List<ToolFinding> findings = List.of(
                new ToolFinding("main.tf", 12, "AWS-0086", "msg"));
        List<MappedFinding> mapped = mapper.mapTrivy(findings);
        assertThat(mapped).hasSize(1);
        assertThat(mapped.get(0).getRuleKey()).isEqualTo("qa-trivy-AWS-0086");
        assertThat(mapped.get(0).getSeverity()).isEqualTo("MAJOR");
    }

    @Test
    void mapsAzureTrivyJsonToAzureRuleKey() {
        String json = resource("trivy-azure-sample.json");
        List<ToolFinding> findings = TrivyOutputParser.parse(json, null);
        List<MappedFinding> mapped = new ToolResultMapper(new RuleRegistry()).mapTrivy(findings);
        assertThat(mapped).hasSize(1);
        MappedFinding finding = mapped.get(0);
        assertThat(finding.getRuleKey()).isEqualTo("qa-trivy-AZU-0052");
        assertThat(finding.getFile()).isEqualTo("roles.tf");
        assertThat(finding.getLine()).isEqualTo(4);
        assertThat(finding.getSeverity()).isEqualTo("MINOR");
        assertThat(finding.getType()).isEqualTo("VULNERABILITY");
    }

    @Test
    void mapsCheckovFindings() {
        RuleRegistry registry = new RuleRegistry();
        ToolResultMapper mapper = new ToolResultMapper(registry);
        List<ToolFinding> findings = List.of(
                new ToolFinding("s3.tf", 5, "CKV_AWS_20", "msg"));
        List<MappedFinding> mapped = mapper.mapCheckov(findings);
        assertThat(mapped).hasSize(1);
        assertThat(mapped.get(0).getRuleKey()).isEqualTo("qa-checkov-CKV_AWS_20");
    }

    @Test
    void mapsCheckovSecretsFindingForPasswordLiteralToSecretRule() {
        String json = resource("checkov-secrets-sample.json");
        List<ToolFinding> findings = CheckovOutputParser.parse(json, "/project");
        List<MappedFinding> mapped = new ToolResultMapper(new RuleRegistry()).mapCheckov(findings);
        assertThat(mapped).hasSize(1);
        MappedFinding secret = mapped.get(0);
        assertThat(secret.getRuleKey()).isEqualTo("qa-checkov-CKV_SECRET_6");
        assertThat(secret.getFile()).isEqualTo("main.tf");
        assertThat(secret.getLine()).isEqualTo(12);
        assertThat(secret.getSeverity()).isEqualTo("CRITICAL");
        assertThat(secret.getType()).isEqualTo("VULNERABILITY");
    }

    @Test
    void skipsUnknownToolRuleId() {
        RuleRegistry registry = new RuleRegistry();
        ToolResultMapper mapper = new ToolResultMapper(registry);
        List<ToolFinding> findings = List.of(
                new ToolFinding("x.tf", 1, "unknown_rule", "msg"));
        List<MappedFinding> mapped = mapper.mapTflint(findings);
        assertThat(mapped).isEmpty();
    }
}
