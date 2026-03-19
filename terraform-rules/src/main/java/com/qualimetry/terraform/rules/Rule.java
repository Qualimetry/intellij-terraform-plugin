package com.qualimetry.terraform.rules;

import java.util.Collections;
import java.util.List;

/**
 * Immutable rule metadata from rules.json.
 */
public final class Rule {

    private final String id;
    private final String name;
    private final String severity;
    private final String type;
    private final String source;
    private final String docUrl;
    private final String descriptionSummary;
    /** VS Code / LSP diagnostic severity: Error, Warning, Information, Hint. Optional; when null use SeverityTranslator from SonarQube severity. */
    private final String vscodeSeverity;
    /** SonarQube tags for filtering (e.g. terraform, tflint, security, aws). Optional. */
    private final List<String> tags;

    public Rule(String id, String name, String severity, String type, String source,
                String docUrl, String descriptionSummary) {
        this(id, name, severity, type, source, docUrl, descriptionSummary, null, null);
    }

    public Rule(String id, String name, String severity, String type, String source,
                String docUrl, String descriptionSummary, String vscodeSeverity) {
        this(id, name, severity, type, source, docUrl, descriptionSummary, vscodeSeverity, null);
    }

    public Rule(String id, String name, String severity, String type, String source,
                String docUrl, String descriptionSummary, String vscodeSeverity, List<String> tags) {
        this.id = id;
        this.name = name;
        this.severity = severity;
        this.type = type;
        this.source = source;
        this.docUrl = docUrl;
        this.descriptionSummary = descriptionSummary;
        this.vscodeSeverity = vscodeSeverity;
        this.tags = tags != null ? List.copyOf(tags) : Collections.emptyList();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSeverity() {
        return severity;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public String getDescriptionSummary() {
        return descriptionSummary;
    }

    /** VS Code / LSP diagnostic severity (Error, Warning, Information, Hint) or null to derive from SonarQube severity. */
    public String getVscodeSeverity() {
        return vscodeSeverity;
    }

    /** SonarQube tags for the rule (never null; may be empty). */
    public List<String> getTags() {
        return tags;
    }
}
