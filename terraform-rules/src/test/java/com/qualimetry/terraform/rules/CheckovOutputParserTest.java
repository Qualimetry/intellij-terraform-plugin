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
    void parsesTerraformAndSecretsReportsFromMultiFrameworkArray() throws Exception {
        String json = resource("checkov-multi-framework-sample.json");
        List<ToolFinding> findings = CheckovOutputParser.parse(json, "/project");
        assertThat(findings).hasSize(3);
        assertThat(findings.get(0).getToolRuleId()).isEqualTo("CKV_AZURE_3");
        assertThat(findings.get(0).getFile()).isEqualTo("storage.tf");
        assertThat(findings.get(0).getLine()).isEqualTo(9);
        assertThat(findings.get(1).getToolRuleId()).isEqualTo("CKV_AZURE_44");
        assertThat(findings.get(2).getToolRuleId()).isEqualTo("CKV_SECRET_2");
        assertThat(findings.get(2).getFile()).isEqualTo("secrets.tf");
        assertThat(findings.get(2).getLine()).isEqualTo(2);
    }

    @Test
    void ignoresReportsOfOtherFrameworks() {
        String json = "[{\"check_type\": \"terraform_plan\", \"results\": {\"failed_checks\": [{\"check_id\": \"CKV_AWS_20\", \"file_path\": \"/plan.json\", \"file_line_range\": [1, 1]}]}}]";
        assertThat(CheckovOutputParser.parse(json, null)).isEmpty();
    }

    @Test
    void arrayReportsWithoutCheckTypeAreParsed() {
        String json = "[{\"results\": {\"failed_checks\": [{\"check_id\": \"CKV_AWS_20\", \"file_path\": \"/s3.tf\", \"file_line_range\": [5, 10]}]}}]";
        List<ToolFinding> findings = CheckovOutputParser.parse(json, null);
        assertThat(findings).hasSize(1);
        assertThat(findings.get(0).getToolRuleId()).isEqualTo("CKV_AWS_20");
        assertThat(findings.get(0).getFile()).isEqualTo("s3.tf");
    }

    @Test
    void emptyOrInvalidReturnsEmpty() {
        assertThat(CheckovOutputParser.parse("", null)).isEmpty();
        assertThat(CheckovOutputParser.parse("{}", null)).isEmpty();
        assertThat(CheckovOutputParser.parse("[]", null)).isEmpty();
    }
}
