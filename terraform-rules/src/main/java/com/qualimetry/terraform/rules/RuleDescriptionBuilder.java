package com.qualimetry.terraform.rules;

/**
 * Builds HTML or Markdown description from rule metadata when a static file is not present.
 * Used as fallback by SonarQube (TerraformRulesDefinition) and LSP (RuleContentService).
 */
public final class RuleDescriptionBuilder {

    private RuleDescriptionBuilder() {
    }

    /**
     * Build minimal HTML description from rule metadata.
     * Used when no static HTML file exists for the rule.
     */
    public static String buildHtml(Rule rule) {
        if (rule == null) return "";
        String summary = rule.getDescriptionSummary();
        if (summary == null) summary = "";
        String docUrl = rule.getDocUrl();
        String docSection = (docUrl != null && !docUrl.isEmpty())
                ? "<p>More information: <a href=\"" + escapeHtml(docUrl) + "\">" + escapeHtml(docUrl) + "</a></p>"
                : "";
        return "<p>" + escapeHtml(summary) + "</p>\n" + docSection;
    }

    /**
     * Build minimal Markdown description from rule metadata.
     * Used when no static Markdown file exists for the rule.
     */
    public static String buildMarkdown(Rule rule) {
        if (rule == null) return "";
        String summary = rule.getDescriptionSummary();
        if (summary == null) summary = "";
        String docUrl = rule.getDocUrl();
        String docSection = (docUrl != null && !docUrl.isEmpty())
                ? "\n\n## See Also\n\nMore information: [" + docUrl + "](" + docUrl + ")"
                : "";
        return summary + docSection;
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
