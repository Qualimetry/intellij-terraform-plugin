package com.qualimetry.terraform.rules;

/**
 * Maps SonarQube severity to VS Code / LSP diagnostic severity when a rule does not
 * define vscodeSeverity explicitly. Used by the VS Code extension (LSP) for diagnostic display.
 * Default mapping aligns with content-pipeline/data/severity-mapping.json (sonarQubeToVscode).
 */
public final class SeverityTranslator {

    private SeverityTranslator() {
    }

    /** SonarQube: BLOCKER, CRITICAL, MAJOR, MINOR, INFO. Returns: Error, Warning, Information, Hint. */
    public static String toVscodeSeverity(String sonarQubeSeverity) {
        if (sonarQubeSeverity == null) return "Information";
        switch (sonarQubeSeverity.toUpperCase()) {
            case "BLOCKER":
            case "CRITICAL":
                return "Error";
            case "MAJOR":
                return "Warning";
            case "MINOR":
                return "Information";
            case "INFO":
            default:
                return "Hint";
        }
    }
}
