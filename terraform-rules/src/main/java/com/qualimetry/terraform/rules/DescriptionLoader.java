package com.qualimetry.terraform.rules;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Loads HTML or Markdown description for a rule key from classpath resources.
 * Paths: /descriptions/html/{ruleKey}.html, /descriptions/markdown/{ruleKey}.md.
 */
public final class DescriptionLoader {

    private static final String HTML_PREFIX = "/descriptions/html/";
    private static final String MARKDOWN_PREFIX = "/descriptions/markdown/";
    private static final String HTML_SUFFIX = ".html";
    private static final String MARKDOWN_SUFFIX = ".md";

    private DescriptionLoader() {
    }

    /**
     * Load HTML description for the rule. Returns null if not found or on error.
     */
    public static String loadHtml(String ruleKey) {
        return loadResource(HTML_PREFIX + ruleKey + HTML_SUFFIX);
    }

    /**
     * Load Markdown description for the rule. Returns null if not found or on error.
     */
    public static String loadMarkdown(String ruleKey) {
        return loadResource(MARKDOWN_PREFIX + ruleKey + MARKDOWN_SUFFIX);
    }

    private static String loadResource(String path) {
        try (InputStream in = DescriptionLoader.class.getResourceAsStream(path)) {
            if (in == null) return null;
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
