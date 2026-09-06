package com.qualimetry.terraform.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps tool findings to our rule keys using RuleRegistry. Rule keys in rules.json
 * use the qa- prefix: qa-tflint-&lt;rule_name&gt;, qa-trivy-&lt;rule_id&gt;, qa-checkov-&lt;check_id&gt;.
 */
public final class ToolResultMapper {

    private static final String RULE_KEY_PREFIX = "qa-";

    private final RuleRegistry registry;

    public ToolResultMapper(RuleRegistry registry) {
        this.registry = registry;
    }

    /**
     * Map a list of tflint findings to MappedFindings. Tool rule id is the tflint rule name.
     */
    public List<MappedFinding> mapTflint(List<ToolFinding> findings) {
        return map(findings, "tflint", f -> RULE_KEY_PREFIX + "tflint-" + f.getToolRuleId());
    }

    /**
     * Map a list of Trivy config findings. Tool rule id is e.g. AWS-0086.
     */
    public List<MappedFinding> mapTrivy(List<ToolFinding> findings) {
        return map(findings, "trivy", f -> RULE_KEY_PREFIX + "trivy-" + f.getToolRuleId());
    }

    /**
     * Map a list of checkov findings. Tool rule id is e.g. CKV_AWS_20.
     */
    public List<MappedFinding> mapCheckov(List<ToolFinding> findings) {
        return map(findings, "checkov", f -> RULE_KEY_PREFIX + "checkov-" + f.getToolRuleId());
    }

    private List<MappedFinding> map(List<ToolFinding> findings, String source,
                                   RuleKeyBuilder keyBuilder) {
        List<MappedFinding> out = new ArrayList<>();
        for (ToolFinding f : findings) {
            String ruleKey = keyBuilder.key(f);
            registry.getRule(ruleKey).ifPresent(rule -> out.add(new MappedFinding(
                    f.getFile(),
                    f.getLine(),
                    ruleKey,
                    f.getMessage(),
                    rule.getSeverity(),
                    rule.getType())));
        }
        return out;
    }

    @FunctionalInterface
    private interface RuleKeyBuilder {
        String key(ToolFinding f);
    }
}
