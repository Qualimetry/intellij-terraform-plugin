package com.qualimetry.terraform.rules;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Parses Trivy config scan JSON output. Expects structure:
 * <pre>
 * {"Results": [{"Target": "main.tf", "Misconfigurations": [
 *   {"ID": "AWS-0086", "Message": "...", "CauseMetadata": {"StartLine": 12}}
 * ]}]}
 * </pre>
 * Use: {@code trivy config -f json .}
 */
public final class TrivyOutputParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TrivyOutputParser() {
    }

    /**
     * Parse Trivy config JSON output and return findings. Returns empty list on parse error.
     */
    public static List<ToolFinding> parse(String json, String projectBasePath) {
        List<ToolFinding> out = new ArrayList<>();
        try {
            JsonNode root = MAPPER.readTree(json);
            JsonNode results = root.get("Results");
            if (results == null) results = root.get("results");
            if (results == null || !results.isArray()) return out;
            for (JsonNode result : results) {
                String target = text(result, "Target");
                if (target == null) target = text(result, "target");
                JsonNode misconfs = result.get("Misconfigurations");
                if (misconfs == null) misconfs = result.get("misconfigurations");
                if (misconfs == null || !misconfs.isArray()) continue;
                for (JsonNode m : misconfs) {
                    String id = text(m, "ID");
                    if (id == null) id = text(m, "Id");
                    String message = text(m, "Message");
                    if (message == null) message = text(m, "Title");
                    int line = 1;
                    JsonNode cause = m.get("CauseMetadata");
                    if (cause != null && cause.has("StartLine")) {
                        line = cause.get("StartLine").asInt(1);
                    }
                    if (id != null) {
                        String normalizedPath = normalizePath(target, projectBasePath);
                        out.add(new ToolFinding(normalizedPath, line, id, message != null ? message : ""));
                    }
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
