package com.qualimetry.terraform.rules;

import java.util.Objects;

/**
 * A finding mapped to our rule key with severity and type from the registry.
 */
public final class MappedFinding {

    private final String file;
    private final int line;
    private final String ruleKey;
    private final String message;
    private final String severity;
    private final String type;

    public MappedFinding(String file, int line, String ruleKey, String message, String severity, String type) {
        this.file = file;
        this.line = line;
        this.ruleKey = ruleKey;
        this.message = message;
        this.severity = severity;
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public String getMessage() {
        return message;
    }

    public String getSeverity() {
        return severity;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedFinding that = (MappedFinding) o;
        return line == that.line
                && Objects.equals(file, that.file)
                && Objects.equals(ruleKey, that.ruleKey)
                && Objects.equals(message, that.message)
                && Objects.equals(severity, that.severity)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, line, ruleKey, message, severity, type);
    }
}
