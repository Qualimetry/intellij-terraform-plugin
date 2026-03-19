package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DescriptionLoaderTest {

    @Test
    void loadHtmlReturnsContentForKnownRule() {
        String html = DescriptionLoader.loadHtml("qa-tflint-terraform_naming_convention");
        assertThat(html).isNotNull().contains("Terraform naming convention").contains("</html>");
    }

    @Test
    void loadMarkdownReturnsContentForKnownRule() {
        String md = DescriptionLoader.loadMarkdown("qa-tflint-terraform_naming_convention");
        assertThat(md).isNotNull().contains("## ").contains("naming convention").contains("Terraform");
    }

    @Test
    void loadReturnsNullForUnknownRule() {
        assertThat(DescriptionLoader.loadHtml("unknown-rule-id")).isNull();
        assertThat(DescriptionLoader.loadMarkdown("unknown-rule-id")).isNull();
    }
}
