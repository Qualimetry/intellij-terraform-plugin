package com.qualimetry.terraform.rules;

/**
 * Computes the public documentation URL for a rule key. The target is the
 * per-rule Markdown page published alongside the plugin snapshot, so a click on
 * any diagnostic from the SonarQube plugin, the LSP server (VS Code), or the
 * JetBrains inspection opens the same page.
 */
public final class RuleHelpUrls {

    public static final String DOCS_BASE_URL =
            "https://github.com/Qualimetry/sonarqube-terraform-plugin/blob/main/docs/rules/";

    private RuleHelpUrls() {
    }

    public static String helpUrl(String ruleKey) {
        return DOCS_BASE_URL + ruleKey + ".md";
    }
}
