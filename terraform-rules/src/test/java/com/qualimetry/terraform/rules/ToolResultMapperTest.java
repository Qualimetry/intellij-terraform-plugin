package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class ToolResultMapperTest {

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
    void skipsUnknownToolRuleId() {
        RuleRegistry registry = new RuleRegistry();
        ToolResultMapper mapper = new ToolResultMapper(registry);
        List<ToolFinding> findings = List.of(
                new ToolFinding("x.tf", 1, "unknown_rule", "msg"));
        List<MappedFinding> mapped = mapper.mapTflint(findings);
        assertThat(mapped).isEmpty();
    }
}
