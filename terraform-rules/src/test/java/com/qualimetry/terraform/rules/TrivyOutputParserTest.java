package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

class TrivyOutputParserTest {

    private static String resource(String name) {
        try (InputStream in = TrivyOutputParserTest.class.getResourceAsStream("/" + name)) {
            return in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    @Test
    void parsesTrivyJson() {
        String json = resource("trivy-sample.json");
        List<ToolFinding> findings = TrivyOutputParser.parse(json, null);
        assertThat(findings).hasSize(2);
        assertThat(findings.get(0).getToolRuleId()).isEqualTo("AWS-0086");
        assertThat(findings.get(0).getFile()).isEqualTo("main.tf");
        assertThat(findings.get(0).getLine()).isEqualTo(12);
        assertThat(findings.get(1).getToolRuleId()).isEqualTo("AWS-0088");
        assertThat(findings.get(1).getLine()).isEqualTo(5);
    }

    @Test
    void emptyOrInvalidReturnsEmpty() {
        assertThat(TrivyOutputParser.parse("", null)).isEmpty();
        assertThat(TrivyOutputParser.parse("{}", null)).isEmpty();
    }
}
