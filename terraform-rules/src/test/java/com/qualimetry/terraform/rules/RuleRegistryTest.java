package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class RuleRegistryTest {

    @Test
    void loadsRulesFromClasspath() {
        RuleRegistry registry = new RuleRegistry();
        List<Rule> all = registry.getAllRules();
        assertThat(all).isNotEmpty();
    }

    @Test
    void getRuleReturnsRuleForKey() {
        RuleRegistry registry = new RuleRegistry();
        Optional<Rule> rule = registry.getRule("qa-tflint-terraform_naming_convention");
        assertThat(rule).isPresent();
        assertThat(rule.get().getName()).isEqualTo("Terraform naming convention");
        assertThat(rule.get().getSeverity()).isEqualTo("MAJOR");
        assertThat(rule.get().getType()).isEqualTo("CODE_SMELL");
    }

    @Test
    void getSeverityAndType() {
        RuleRegistry registry = new RuleRegistry();
        assertThat(registry.getSeverity("qa-trivy-AWS-0086")).isEqualTo("MAJOR");
        assertThat(registry.getType("qa-trivy-AWS-0086")).isEqualTo("VULNERABILITY");
    }

    @Test
    void getRuleReturnsEmptyForUnknownKey() {
        RuleRegistry registry = new RuleRegistry();
        assertThat(registry.getRule("unknown-rule")).isEmpty();
    }

    @Test
    void repositoryKey() {
        assertThat(RuleRegistry.getRepositoryKey()).isEqualTo("qualimetry-terraform");
    }

    @Test
    void qualimetryTerraformProfileExcludesSonarWayDuplicates() {
        RuleRegistry registry = new RuleRegistry();
        List<String> qt = registry.getQualimetryTerraformRuleKeys();
        // Should not contain rules that duplicate Sonar Way Terraform (S3/Azure public access, logging, encryption)
        assertThat(qt).doesNotContain("qa-trivy-AWS-0086", "qa-trivy-AWS-0087", "qa-trivy-AWS-0088", "qa-trivy-AWS-0089",
                "qa-checkov-CKV_AWS_20", "qa-checkov-CKV_AWS_57", "qa-checkov-CKV2_AWS_6");
        // Should contain tflint rules (no Sonar Way overlap)
        assertThat(qt).contains("qa-tflint-terraform_naming_convention", "qa-tflint-terraform_module_pinned_source",
                "qa-tflint-terraform_required_version", "qa-tflint-aws_instance_invalid_type");
    }

    @Test
    void qualimetryWayProfileContainsAllRules() {
        RuleRegistry registry = new RuleRegistry();
        List<String> way = registry.getQualimetryWayRuleKeys();
        assertThat(way).containsAll(registry.getQualimetryTerraformRuleKeys());
        assertThat(way).contains("qa-trivy-AWS-0086", "qa-checkov-CKV_AWS_20");
        assertThat(way).hasSize(registry.getAllRules().size());
    }
}
