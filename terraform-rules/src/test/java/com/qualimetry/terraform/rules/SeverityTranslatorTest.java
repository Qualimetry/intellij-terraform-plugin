package com.qualimetry.terraform.rules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SeverityTranslatorTest {

    @Test
    void mapsBlockerAndCriticalToError() {
        assertThat(SeverityTranslator.toVscodeSeverity("BLOCKER")).isEqualTo("Error");
        assertThat(SeverityTranslator.toVscodeSeverity("CRITICAL")).isEqualTo("Error");
    }

    @Test
    void mapsMajorToWarning() {
        assertThat(SeverityTranslator.toVscodeSeverity("MAJOR")).isEqualTo("Warning");
    }

    @Test
    void mapsMinorToInformation() {
        assertThat(SeverityTranslator.toVscodeSeverity("MINOR")).isEqualTo("Information");
    }

    @Test
    void mapsInfoToHint() {
        assertThat(SeverityTranslator.toVscodeSeverity("INFO")).isEqualTo("Hint");
    }

    @Test
    void nullReturnsInformation() {
        assertThat(SeverityTranslator.toVscodeSeverity(null)).isEqualTo("Information");
    }

    @Test
    void unknownReturnsHint() {
        assertThat(SeverityTranslator.toVscodeSeverity("UNKNOWN")).isEqualTo("Hint");
    }
}
