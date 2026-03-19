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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

class RuleTest {

    @Test
    void allFieldConstructorAndGetters() {
        Rule rule = new Rule("r1", "Rule One", "MAJOR", "BUG", "tflint",
                "https://doc.example.com", "summary text", "Warning", List.of("terraform", "aws"));

        assertThat(rule.getId()).isEqualTo("r1");
        assertThat(rule.getName()).isEqualTo("Rule One");
        assertThat(rule.getSeverity()).isEqualTo("MAJOR");
        assertThat(rule.getType()).isEqualTo("BUG");
        assertThat(rule.getSource()).isEqualTo("tflint");
        assertThat(rule.getDocUrl()).isEqualTo("https://doc.example.com");
        assertThat(rule.getDescriptionSummary()).isEqualTo("summary text");
        assertThat(rule.getVscodeSeverity()).isEqualTo("Warning");
        assertThat(rule.getTags()).containsExactly("terraform", "aws");
    }

    @Test
    void minimalConstructorSetsDefaults() {
        Rule rule = new Rule("r2", "Rule Two", "MINOR", "CODE_SMELL", "trivy",
                null, "desc");

        assertThat(rule.getId()).isEqualTo("r2");
        assertThat(rule.getVscodeSeverity()).isNull();
        assertThat(rule.getTags()).isEmpty();
    }

    @Test
    void twoArgVscodeSeverityConstructor() {
        Rule rule = new Rule("r3", "Rule Three", "CRITICAL", "VULNERABILITY", "checkov",
                "https://example.com", "desc", "Error");

        assertThat(rule.getVscodeSeverity()).isEqualTo("Error");
        assertThat(rule.getTags()).isEmpty();
    }

    @Test
    void tagsAreImmutable() {
        List<String> mutableTags = new java.util.ArrayList<>(List.of("a", "b"));
        Rule rule = new Rule("r4", "name", "MAJOR", "BUG", "tflint",
                null, null, null, mutableTags);

        mutableTags.add("c");
        assertThat(rule.getTags()).containsExactly("a", "b");

        assertThatThrownBy(() -> rule.getTags().add("d"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void nullTagsDefaultsToEmptyList() {
        Rule rule = new Rule("r5", "name", "MAJOR", "BUG", "tflint",
                null, null, null, null);

        assertThat(rule.getTags()).isNotNull().isEmpty();
    }

    @Test
    void nullFieldsAllowed() {
        Rule rule = new Rule("r6", null, null, null, null, null, null);

        assertThat(rule.getId()).isEqualTo("r6");
        assertThat(rule.getName()).isNull();
        assertThat(rule.getSeverity()).isNull();
        assertThat(rule.getType()).isNull();
        assertThat(rule.getSource()).isNull();
        assertThat(rule.getDocUrl()).isNull();
        assertThat(rule.getDescriptionSummary()).isNull();
    }
}
