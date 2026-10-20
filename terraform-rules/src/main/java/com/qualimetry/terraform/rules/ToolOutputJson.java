package com.qualimetry.terraform.rules;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Locates the JSON document inside raw tool output. Tools write log lines, banners and progress text ahead of
 * the document, and stderr is often merged into stdout by the caller.
 */
public final class ToolOutputJson {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ToolOutputJson() {
    }

    /**
     * Returns the output from the first '{' or '[' that starts a parseable JSON document, or null when there is none.
     * Text after the document is kept; the parsers read a single root value and ignore trailing content.
     */
    public static String extract(String output) {
        if (output == null || output.isEmpty()) return null;
        for (int i = nextStart(output, 0); i >= 0; i = nextStart(output, i + 1)) {
            String candidate = output.substring(i);
            if (parses(candidate)) return candidate;
        }
        return null;
    }

    private static int nextStart(String output, int from) {
        for (int i = from; i < output.length(); i++) {
            char c = output.charAt(i);
            if (c == '{' || c == '[') return i;
        }
        return -1;
    }

    private static boolean parses(String candidate) {
        try {
            return MAPPER.readTree(candidate) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
