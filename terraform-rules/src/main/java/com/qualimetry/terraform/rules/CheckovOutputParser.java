package com.qualimetry.terraform.rules;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Parses checkov -d . --output json. Expects structure with results key containing list of checks:
 * {"results": {"passed_checks": [...], "failed_checks": [{"check_id": "CKV_AWS_20", "file_path": "/path/to/file.tf", "file_line_range": [1, 2]}]}}
 * We only parse failed_checks. file_line_range[0] is used as the line.
 */
public final class CheckovOutputParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CheckovOutputParser() {
    }

    /**
     * Parse checkov JSON output and return findings. Returns empty list on parse error.
     */
    public static List<ToolFinding> parse(String json, String projectBasePath) {
        List<ToolFinding> out = new ArrayList<>();
        try {
            JsonNode root = MAPPER.readTree(json);
            JsonNode results = root.get("results");
            if (results == null) return out;
            JsonNode failed = results.get("failed_checks");
            if (failed == null || !failed.isArray()) return out;
            for (JsonNode c : failed) {
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
                if (checkId != null && filePath != null) {
                    String normalizedPath = normalizePath(filePath, projectBasePath);
                    if (normalizedPath.isEmpty()) normalizedPath = filePath;
                    out.add(new ToolFinding(normalizedPath, line, checkId, message != null ? message : ""));
                }
            }
        } catch (Exception e) {
            // Return empty on parse error
        }
        return out;
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
