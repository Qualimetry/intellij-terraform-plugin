package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ToolOutputJsonTest {

    @Test
    void returnsTheDocumentWhenOutputIsOnlyJson() {
        assertThat(ToolOutputJson.extract("{\"issues\": []}")).isEqualTo("{\"issues\": []}");
        assertThat(ToolOutputJson.extract("[{\"check_type\": \"terraform\"}]")).isEqualTo("[{\"check_type\": \"terraform\"}]");
    }

    @Test
    void skipsLogLinesAheadOfAnObject() {
        String output = "2026-10-20T10:00:00Z\tINFO\t[misconfig] Misconfiguration scanning is enabled\n"
                + "2026-10-20T10:00:01Z\tINFO\t[checks-client] Downloading the checks bundle...\n"
                + "{\"Results\": []}\n";
        assertThat(ToolOutputJson.extract(output)).isEqualTo("{\"Results\": []}\n");
    }

    @Test
    void skipsBannerLinesWithBracketsAheadOfAnArray() {
        String output = "[ checkov ] update available: 3.3.30\n[{\"check_type\": \"terraform\", \"results\": {}}]";
        assertThat(ToolOutputJson.extract(output)).isEqualTo("[{\"check_type\": \"terraform\", \"results\": {}}]");
    }

    @Test
    void keepsTrailingTextAfterTheDocument() {
        assertThat(ToolOutputJson.extract("banner\n{\"a\": 1}\ntrailing text")).isEqualTo("{\"a\": 1}\ntrailing text");
    }

    @Test
    void returnsNullWithoutAParseableDocument() {
        assertThat(ToolOutputJson.extract(null)).isNull();
        assertThat(ToolOutputJson.extract("")).isNull();
        assertThat(ToolOutputJson.extract("no json here at all")).isNull();
        assertThat(ToolOutputJson.extract("[ checkov ] update available: 3.3.30\n{\"truncated\": ")).isNull();
    }
}
