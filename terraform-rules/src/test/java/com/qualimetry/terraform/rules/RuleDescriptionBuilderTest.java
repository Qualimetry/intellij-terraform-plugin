/*
 * Copyright 2026 SHAZAM Analytics Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RuleDescriptionBuilderTest {

    private Rule ruleWith(String summary, String docUrl) {
        return new Rule("test-rule", "Test Rule", "MAJOR", "CODE_SMELL", "tflint",
                docUrl, summary);
    }

    @Test
    void buildHtmlWithSummaryAndDocUrl() {
        Rule rule = ruleWith("Enforce naming conventions", "https://example.com/doc");
        String html = RuleDescriptionBuilder.buildHtml(rule);

        assertThat(html).contains("<p>Enforce naming conventions</p>");
        assertThat(html).contains("<a href=\"https://example.com/doc\">");
        assertThat(html).contains("https://example.com/doc</a>");
    }

    @Test
    void buildHtmlWithSummaryOnly() {
        Rule rule = ruleWith("Enforce naming conventions", null);
        String html = RuleDescriptionBuilder.buildHtml(rule);

        assertThat(html).contains("<p>Enforce naming conventions</p>");
        assertThat(html).doesNotContain("<a href=");
    }

    @Test
    void buildHtmlWithEmptyDocUrl() {
        Rule rule = ruleWith("Summary", "");
        String html = RuleDescriptionBuilder.buildHtml(rule);

        assertThat(html).contains("<p>Summary</p>");
        assertThat(html).doesNotContain("<a href=");
    }

    @Test
    void buildHtmlWithNullSummary() {
        Rule rule = ruleWith(null, "https://example.com");
        String html = RuleDescriptionBuilder.buildHtml(rule);

        assertThat(html).contains("<p></p>");
        assertThat(html).contains("https://example.com");
    }

    @Test
    void buildHtmlNullRuleReturnsEmpty() {
        String html = RuleDescriptionBuilder.buildHtml(null);
        assertThat(html).isEmpty();
    }

    @Test
    void buildHtmlEscapesSpecialCharacters() {
        Rule rule = ruleWith("Use <b>tags</b> & \"quotes\"", null);
        String html = RuleDescriptionBuilder.buildHtml(rule);

        assertThat(html).contains("&lt;b&gt;tags&lt;/b&gt;");
        assertThat(html).contains("&amp;");
        assertThat(html).contains("&quot;quotes&quot;");
        assertThat(html).doesNotContain("<b>");
    }

    @Test
    void buildMarkdownWithSummaryAndDocUrl() {
        Rule rule = ruleWith("Enforce naming conventions", "https://example.com/doc");
        String md = RuleDescriptionBuilder.buildMarkdown(rule);

        assertThat(md).startsWith("Enforce naming conventions");
        assertThat(md).contains("## See Also");
        assertThat(md).contains("[https://example.com/doc](https://example.com/doc)");
    }

    @Test
    void buildMarkdownWithSummaryOnly() {
        Rule rule = ruleWith("Enforce naming conventions", null);
        String md = RuleDescriptionBuilder.buildMarkdown(rule);

        assertThat(md).isEqualTo("Enforce naming conventions");
        assertThat(md).doesNotContain("See Also");
    }

    @Test
    void buildMarkdownWithEmptyDocUrl() {
        Rule rule = ruleWith("Summary text", "");
        String md = RuleDescriptionBuilder.buildMarkdown(rule);

        assertThat(md).isEqualTo("Summary text");
        assertThat(md).doesNotContain("See Also");
    }

    @Test
    void buildMarkdownNullRuleReturnsEmpty() {
        String md = RuleDescriptionBuilder.buildMarkdown(null);
        assertThat(md).isEmpty();
    }

    @Test
    void buildMarkdownWithNullSummary() {
        Rule rule = ruleWith(null, "https://example.com");
        String md = RuleDescriptionBuilder.buildMarkdown(rule);

        assertThat(md).contains("## See Also");
        assertThat(md).contains("https://example.com");
    }
}
