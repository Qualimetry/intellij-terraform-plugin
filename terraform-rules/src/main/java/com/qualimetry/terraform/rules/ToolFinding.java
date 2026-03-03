package com.qualimetry.terraform.rules;

import java.util.Objects;

/**
 * A single finding from a tool (tflint/trivy/checkov): file, line, tool rule id, message.
 */
public final class ToolFinding {

    private final String file;
    private final int line;
    private final String toolRuleId;
    private final String message;

    public ToolFinding(String file, int line, String toolRuleId, String message) {
        this.file = file;
        this.line = line;
        this.toolRuleId = toolRuleId;
        this.message = message;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public String getToolRuleId() {
        return toolRuleId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolFinding that = (ToolFinding) o;
        return line == that.line
                && Objects.equals(file, that.file)
                && Objects.equals(toolRuleId, that.toolRuleId)
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, line, toolRuleId, message);
    }
}
