package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

class TflintOutputParserTest {

    private static String resource(String name) {
        try (InputStream in = TflintOutputParserTest.class.getResourceAsStream("/" + name)) {
            return in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    @Test
    void parsesTflintJson() throws Exception {
        String json = resource("tflint-sample.json");
        List<ToolFinding> findings = TflintOutputParser.parse(json, null);
        assertThat(findings).hasSize(2);
        assertThat(findings.get(0).getToolRuleId()).isEqualTo("terraform_naming_convention");
        assertThat(findings.get(0).getMessage()).contains("naming convention");
        assertThat(findings.get(0).getFile()).isEqualTo("main.tf");
        assertThat(findings.get(0).getLine()).isEqualTo(3);
        assertThat(findings.get(1).getToolRuleId()).isEqualTo("terraform_module_pinned_source");
        assertThat(findings.get(1).getFile()).isEqualTo("modules/foo/main.tf");
        assertThat(findings.get(1).getLine()).isEqualTo(1);
    }

    @Test
    void normalizesPathWithBase() throws Exception {
        String json = resource("tflint-sample.json");
        List<ToolFinding> findings = TflintOutputParser.parse(json, "/project");
        assertThat(findings).hasSize(2);
        // Paths in sample don't start with /project so unchanged
        assertThat(findings.get(0).getFile()).isEqualTo("main.tf");
    }

    @Test
    void emptyOrInvalidReturnsEmpty() {
        assertThat(TflintOutputParser.parse("", null)).isEmpty();
        assertThat(TflintOutputParser.parse("{}", null)).isEmpty();
        assertThat(TflintOutputParser.parse("invalid", null)).isEmpty();
    }
}
