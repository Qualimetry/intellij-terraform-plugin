package com.qualimetry.terraform.rules;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Parses checkov -d . --output json. A single report is an object:
 * {"check_type": "terraform", "results": {"passed_checks": [...], "failed_checks": [{"check_id": "CKV_AWS_20", "file_path": "/path/to/file.tf", "file_line_range": [1, 2]}]}}
 * When more than one framework produced results (for example terraform plus secrets) checkov emits an array of such reports.
 * Only failed_checks of terraform and secrets reports are parsed (plan and other framework reports are ignored);
 * a report without check_type is treated as terraform. file_line_range[0] is used as the line.
 */
public final class CheckovOutputParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String TERRAFORM_CHECK_TYPE = "terraform";
    private static final String SECRETS_CHECK_TYPE = "secrets";

    private CheckovOutputParser() {
    }

    /**
     * Parse checkov JSON output and return findings. Returns empty list on parse error.
     */
    public static List<ToolFinding> parse(String json, String projectBasePath) {
        List<ToolFinding> out = new ArrayList<>();
        try {
            JsonNode root = MAPPER.readTree(json);
            if (root == null) return out;
            if (root.isArray()) {
                for (JsonNode report : root) {
                    if (isTerraformSourceReport(report)) collectFailedChecks(report, projectBasePath, out);
                }
            } else {
                collectFailedChecks(root, projectBasePath, out);
            }
        } catch (Exception e) {
            // Return empty on parse error
        }
        return out;
    }

    private static boolean isTerraformSourceReport(JsonNode report) {
        String checkType = text(report, "check_type");
        return checkType == null || TERRAFORM_CHECK_TYPE.equals(checkType) || SECRETS_CHECK_TYPE.equals(checkType);
    }

    private static void collectFailedChecks(JsonNode report, String projectBasePath, List<ToolFinding> out) {
        JsonNode results = report.get("results");
        if (results == null) return;
        JsonNode failed = results.get("failed_checks");
        if (failed == null || !failed.isArray()) return;
        for (JsonNode check : failed) {
            ToolFinding finding = toFinding(check, projectBasePath);
            if (finding != null) out.add(finding);
        }
    }

    private static ToolFinding toFinding(JsonNode c, String projectBasePath) {
        String checkId = text(c, "check_id");
        String message = text(c, "message");
        if (message == null) message = text(c, "check_name");
        // Prefer file_abs_path (full path) so normalization works on all platforms.
        // Otherwise use file_path: Windows may return "\\main.tf", Unix/macOS "/path/to/main.tf" or "main.tf".
        String filePath = text(c, "file_abs_path");
        if (filePath == null || filePath.isEmpty()) {
            filePath = text(c, "file_path");
            // Strip leading slashes only when path is a single segment (e.g. "\main.tf" on Windows, "/main.tf" on Unix)
            if (filePath != null && filePath.replace('\\', '/').matches("^/+[^/]+$")) {
                filePath = filePath.replace('\\', '/').replaceAll("^/+", "");
            }
        }
        if (filePath != null) filePath = filePath.replace('\\', '/');
        int line = 1;
        JsonNode range = c.get("file_line_range");
        if (range != null && range.isArray() && range.size() > 0) {
            line = range.get(0).asInt(1);
        }
        if (checkId == null || filePath == null) return null;
        String normalizedPath = normalizePath(filePath, projectBasePath);
        if (normalizedPath.isEmpty()) normalizedPath = filePath;
        return new ToolFinding(normalizedPath, line, checkId, message != null ? message : "");
    }

    private static String text(JsonNode node, String key) {
        JsonNode n = node.get(key);
        return n != null && n.isTextual() ? n.asText() : null;
    }

    private static String normalizePath(String path, String projectBasePath) {
        if (path == null || path.isEmpty()) return "";
        // Use forward slashes for comparison so paths work on Windows, macOS, and Linux
        String normalized = path.replace('\\', '/');
        if (projectBasePath != null && !projectBasePath.isEmpty()) {
            String base = projectBasePath.replace('\\', '/');
            if (!base.endsWith("/")) base = base + "/";
            if (normalized.startsWith(base)) {
                normalized = normalized.substring(base.length());
            }
        }
        return normalized;
    }
}
