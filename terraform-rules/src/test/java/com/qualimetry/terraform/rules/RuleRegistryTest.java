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
        assertThat(registry.getSeverity("qa-trivy-AZU-0052")).isEqualTo("MINOR");
        assertThat(registry.getType("qa-trivy-AZU-0052")).isEqualTo("VULNERABILITY");
        assertThat(registry.getSeverity("qa-trivy-AZU-0047")).isEqualTo("CRITICAL");
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
                "qa-checkov-CKV_AWS_20", "qa-checkov-CKV_AWS_57", "qa-checkov-CKV2_AWS_6", "qa-trivy-AZU-0036");
        assertThat(qt).contains("qa-trivy-AZU-0052", "qa-trivy-AZU-0038");
        // Should contain tflint rules (no Sonar Way overlap)
        assertThat(qt).contains("qa-tflint-terraform_naming_convention", "qa-tflint-terraform_module_pinned_source",
                "qa-tflint-terraform_required_version", "qa-tflint-aws_instance_invalid_type");
    }

    @Test
    void providerProfilesIncludeTheCheckovSecretsRules() {
        RuleRegistry registry = new RuleRegistry();
        assertThat(registry.getQualimetryAWSWayRuleKeys()).contains("qa-checkov-CKV_SECRET_6", "qa-checkov-CKV_SECRET_10", "qa-checkov-CKV_SECRET_19");
        assertThat(registry.getQualimetryAzureWayRuleKeys()).contains("qa-checkov-CKV_SECRET_6", "qa-checkov-CKV_SECRET_10", "qa-checkov-CKV_SECRET_19");
        assertThat(registry.getQualimetryAzureWayRuleKeys()).doesNotContain("qa-checkov-CKV_AWS_20");
        assertThat(registry.getQualimetryTerraformRuleKeys()).contains("qa-checkov-CKV_SECRET_6", "qa-checkov-CKV_AWS_41");
        assertThat(registry.getRule("qa-checkov-CKV_SECRET_6")).hasValueSatisfying(rule -> {
            assertThat(rule.getSeverity()).isEqualTo("CRITICAL");
            assertThat(rule.getType()).isEqualTo("VULNERABILITY");
            assertThat(rule.getTags()).contains("security", "secrets");
        });
    }

    @Test
    void providerProfilesIncludeOnlyTheirOwnTrivyRules() {
        RuleRegistry registry = new RuleRegistry();
        List<String> aws = registry.getQualimetryAWSWayRuleKeys();
        List<String> azure = registry.getQualimetryAzureWayRuleKeys();
        assertThat(aws).contains("qa-trivy-AWS-0086").doesNotContain("qa-trivy-AZU-0052");
        assertThat(azure).contains("qa-trivy-AZU-0052", "qa-trivy-AZU-0001", "qa-trivy-AZU-0076").doesNotContain("qa-trivy-AWS-0086");
        assertThat(aws.stream().filter(k -> k.startsWith("qa-trivy-"))).allMatch(k -> k.startsWith("qa-trivy-AWS-")).hasSize(12);
        assertThat(azure.stream().filter(k -> k.startsWith("qa-trivy-"))).allMatch(k -> k.startsWith("qa-trivy-AZU-")).hasSize(73);
    }

    @Test
    void profileSizesMatchTheCatalogue() {
        RuleRegistry registry = new RuleRegistry();
        assertThat(registry.getAllRules()).hasSize(858);
        assertThat(registry.getQualimetryWayRuleKeys()).hasSize(858);
        assertThat(registry.getQualimetryTerraformRuleKeys()).hasSize(850);
        assertThat(registry.getQualimetryAWSWayRuleKeys()).hasSize(539);
        assertThat(registry.getQualimetryAzureWayRuleKeys()).hasSize(392);
    }

    @Test
    void qualimetryWayProfileContainsAllRules() {
        RuleRegistry registry = new RuleRegistry();
        List<String> way = registry.getQualimetryWayRuleKeys();
        assertThat(way).containsAll(registry.getQualimetryTerraformRuleKeys());
        assertThat(way).contains("qa-trivy-AWS-0086", "qa-trivy-AZU-0052", "qa-checkov-CKV_AWS_20");
        assertThat(way).hasSize(registry.getAllRules().size());
    }
}
