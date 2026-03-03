package com.qualimetry.terraform.rules;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Loads rule metadata from rules.json on the classpath and exposes lookup by rule key.
 */
public final class RuleRegistry {

    public static final String REPOSITORY_KEY = "qualimetry-terraform";

    /**
     * Qualimetry rule keys that duplicate Sonar Way Terraform rules (same or very similar finding).
     * Excluded from the "Qualimetry Terraform" profile so that profile does not duplicate Sonar Way.
     * Sonar Way themes: S3 public ACLs/policies, S3 logging, S3/cloud encryption, S3 public access block, Azure storage encryption.
     */
    private static final Set<String> DUPLICATES_SONAR_WAY_TERRAFORM = Set.of(
            "qa-trivy-AWS-0086",   // S3 public access block → Sonar "Allowing public ACLs or policies on a S3 bucket"
            "qa-trivy-AWS-0087",   // S3 public policy block
            "qa-trivy-AWS-0088",   // S3 encryption → Sonar "Using unencrypted cloud storages"
            "qa-trivy-AWS-0089",   // S3 logging → Sonar "Disabling logging is security-sensitive"
            "qa-checkov-CKV_AWS_20",  // S3 encryption
            "qa-checkov-CKV_AWS_57",  // S3 public access block → Sonar "Granting access to S3 buckets to all or authenticated users"
            "qa-checkov-CKV2_AWS_6"   // S3 public access block
    );

    private final Map<String, Rule> rulesById;

    public RuleRegistry() {
        this.rulesById = loadRules();
    }

    private static Map<String, Rule> loadRules() {
        Map<String, Rule> map = new ConcurrentHashMap<>();
        try (InputStream in = RuleRegistry.class.getResourceAsStream("/rules.json")) {
            if (in == null) {
                return map;
            }
            String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode rules = root.get("rules");
            if (rules != null && rules.isArray()) {
                for (JsonNode r : rules) {
                    String id = text(r, "id");
                    if (id == null || id.isEmpty()) continue;
                    List<String> tagList = stringList(r, "tags");
                    Rule rule = new Rule(
                            id,
                            text(r, "name"),
                            text(r, "severity"),
                            text(r, "type"),
                            text(r, "source"),
                            text(r, "docUrl"),
                            text(r, "descriptionSummary"),
                            text(r, "vscodeSeverity"),
                            tagList);
                    map.put(id, rule);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load rules.json", e);
        }
        return map;
    }

    private static String text(JsonNode node, String key) {
        JsonNode n = node.get(key);
        if (n == null || n.isNull()) return null;
        String s = n.asText();
        return s.isEmpty() ? null : s;
    }

    private static List<String> stringList(JsonNode node, String key) {
        JsonNode n = node.get(key);
        if (n == null || !n.isArray()) return List.of();
        List<String> out = new ArrayList<>();
        for (JsonNode item : n) {
            if (item != null && item.isTextual()) {
                String s = item.asText();
                if (s != null && !s.isEmpty()) out.add(s);
            }
        }
        return out;
    }

    public Optional<Rule> getRule(String ruleKey) {
        return Optional.ofNullable(rulesById.get(ruleKey));
    }

    public List<Rule> getAllRules() {
        return Collections.unmodifiableList(new ArrayList<>(rulesById.values()));
    }

    /**
     * Rule keys for the "Qualimetry Terraform" profile. This profile does not duplicate
     * rules already active in Sonar Way for Terraform. Excludes keys in DUPLICATES_SONAR_WAY_TERRAFORM.
     */
    public List<String> getQualimetryTerraformRuleKeys() {
        return rulesById.keySet().stream()
                .filter(k -> !DUPLICATES_SONAR_WAY_TERRAFORM.contains(k))
                .sorted()
                .toList();
    }

    /**
     * Rule keys for the "Qualimetry Way" profile (recommended full set).
     * Currently returns all known rules.
     */
    public List<String> getQualimetryWayRuleKeys() {
        return rulesById.keySet().stream().sorted().toList();
    }

    /**
     * Rule keys for the "Qualimetry AWS Way" profile: tflint, Trivy, and Checkov AWS (CKV_AWS_*, CKV2_AWS_*).
     */
    public List<String> getQualimetryAWSWayRuleKeys() {
        return rulesById.keySet().stream()
                .filter(k -> k.startsWith("qa-tflint-") || k.startsWith("qa-trivy-")
                        || k.startsWith("qa-checkov-CKV_AWS_") || k.startsWith("qa-checkov-CKV2_AWS_"))
                .sorted()
                .toList();
    }

    /**
     * Rule keys for the "Qualimetry Azure Way" profile: tflint, Trivy, and Checkov CKV_AZURE_* (azurerm).
     * Same shape as Qualimetry AWS Way but for Azure-focused projects.
     */
    public List<String> getQualimetryAzureWayRuleKeys() {
        return rulesById.keySet().stream()
                .filter(k -> k.startsWith("qa-tflint-") || k.startsWith("qa-trivy-")
                        || k.startsWith("qa-checkov-CKV_AZURE_"))
                .sorted()
                .toList();
    }

    public String getSeverity(String ruleKey) {
        return getRule(ruleKey).map(Rule::getSeverity).orElse(null);
    }

    public String getType(String ruleKey) {
        return getRule(ruleKey).map(Rule::getType).orElse(null);
    }

    /** VS Code diagnostic severity for the rule key, or null if unknown. */
    public String getVscodeSeverity(String ruleKey) {
        return getRule(ruleKey).map(Rule::getVscodeSeverity).orElse(null);
    }

    public static String getRepositoryKey() {
        return REPOSITORY_KEY;
    }
}
