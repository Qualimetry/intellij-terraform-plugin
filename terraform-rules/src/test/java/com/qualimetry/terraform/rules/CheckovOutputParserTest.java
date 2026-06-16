package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

class CheckovOutputParserTest {

    private static String resource(String name) {
        try (InputStream in = CheckovOutputParserTest.class.getResourceAsStream("/" + name)) {
            return in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    @Test
    void parsesCheckovJson() throws Exception {
        String json = resource("checkov-sample.json");
        List<ToolFinding> findings = CheckovOutputParser.parse(json, "/project");
        assertThat(findings).hasSize(2);
        assertThat(findings.get(0).getToolRuleId()).isEqualTo("CKV_AWS_20");
        assertThat(findings.get(0).getFile()).isEqualTo("s3.tf");
        assertThat(findings.get(0).getLine()).isEqualTo(5);
        assertThat(findings.get(1).getToolRuleId()).isEqualTo("CKV_AWS_57");
    }

    @Test
    void emptyOrInvalidReturnsEmpty() {
        assertThat(CheckovOutputParser.parse("", null)).isEmpty();
        assertThat(CheckovOutputParser.parse("{}", null)).isEmpty();
    }
}
