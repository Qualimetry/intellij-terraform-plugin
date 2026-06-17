package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Every catalogue rule must have a published help page and a non-null help URL,
 * so the clickable IDE help links always resolve to a real document.
 */
class DocsParityTest {

    private static File docsRulesDir() {
        File dir = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (dir != null) {
            File candidate = new File(dir, "docs/rules");
            if (candidate.isDirectory()) {
                return candidate;
            }
            dir = dir.getParentFile();
        }
        throw new IllegalStateException("docs/rules directory not found above " + System.getProperty("user.dir"));
    }

    @Test
    void everyRuleHasHelpPageAndHelpUrl() {
        RuleRegistry registry = new RuleRegistry();
        List<Rule> rules = registry.getAllRules();
        assertThat(rules).isNotEmpty();

        File docsRules = docsRulesDir();
        List<String> missingDoc = new ArrayList<>();
        List<String> badHelpUrl = new ArrayList<>();

        for (Rule rule : rules) {
            String id = rule.getId();
            if (!new File(docsRules, id + ".md").isFile()) {
                missingDoc.add(id);
            }
            String helpUrl = rule.getHelpUrl();
            if (helpUrl == null || !helpUrl.equals(RuleRegistry.helpUrlFor(id))) {
                badHelpUrl.add(id + " -> " + helpUrl);
            }
        }

        assertThat(missingDoc).as("rules missing docs/rules/<id>.md").isEmpty();
        assertThat(badHelpUrl).as("rules with missing or unexpected helpUrl").isEmpty();
    }

    @Test
    void helpUrlNeverNullEvenForUnknownKey() {
        RuleRegistry registry = new RuleRegistry();
        assertThat(registry.getHelpUrl("qa-does-not-exist")).isNotNull().endsWith("/qa-does-not-exist.md");
    }
}
