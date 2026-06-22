package com.qualimetry.terraform.rules;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Parses tflint --format json output. Expects structure:
 * {"issues": [{"rule": {"name": "rule_id"}, "message": "...", "range": {"filename": "...", "start": {"line": n}}}]}
 * Paths in output are normalized to forward slashes and made relative when possible.
 */
public final class TflintOutputParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TflintOutputParser() {
    }

    /**
     * Parse tflint JSON output and return findings. Returns empty list on parse error or empty output.
     */
    public static List<ToolFinding> parse(String json, String projectBasePath) {
        List<ToolFinding> out = new ArrayList<>();
        try {
            JsonNode root = MAPPER.readTree(json);
            JsonNode issues = root.get("issues");
            if (issues == null) issues = root.get("Issues");
            if (issues == null || !issues.isArray()) return out;
            for (JsonNode issue : issues) {
                String ruleName = ruleName(issue);
                String message = text(issue, "message");
                String filename = filename(issue);
                int line = line(issue);
                if (ruleName != null) {
                    String normalizedPath = normalizePath(filename, projectBasePath);
                    out.add(new ToolFinding(normalizedPath, line, ruleName, message != null ? message : ""));
                }
            }
        } catch (Exception e) {
            // Return empty on any parse error
        }
        return out;
    }

    private static String ruleName(JsonNode issue) {
        JsonNode rule = issue.get("rule");
        if (rule == null) return null;
        return text(rule, "name");
    }

    private static String filename(JsonNode issue) {
        JsonNode range = issue.get("range");
        if (range == null) return null;
        return text(range, "filename");
    }

    private static int line(JsonNode issue) {
        JsonNode range = issue.get("range");
        if (range == null) return 1;
        JsonNode start = range.get("start");
        if (start == null) return 1;
        JsonNode line = start.get("line");
        return line != null && line.isNumber() ? line.asInt(1) : 1;
    }

    private static String text(JsonNode node, String key) {
        JsonNode n = node.get(key);
        return n != null && n.isTextual() ? n.asText() : null;
    }

    private static String normalizePath(String path, String projectBasePath) {
        if (path == null || path.isEmpty()) return "";
        // Forward slashes for cross-platform comparison (Windows, macOS, Linux)
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
