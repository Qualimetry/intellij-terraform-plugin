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
package com.qualimetry.intellij.terraform;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.qualimetry.terraform.rules.RuleRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Persistent settings for the Qualimetry Terraform Analyzer plugin.
 * Stores per-rule enabled/disabled overrides and severity overrides.
 */
@State(name = "QualimetryTerraformAnalyzerSettings", storages = @Storage("qualimetry-terraform.xml"))
public final class TerraformAnalyzerSettings implements PersistentStateComponent<TerraformAnalyzerSettings> {

    public boolean enabled = true;
    public boolean rulesReplaceDefaults = false;
    public String sonarQubeUrl = "";
    public String sonarQubeProfile = "";

    public Map<String, RuleOverride> rules = new HashMap<>();

    private static Set<String> defaultProfile;

    public static TerraformAnalyzerSettings getInstance() {
        return ApplicationManager.getApplication().getService(TerraformAnalyzerSettings.class);
    }

    @Override
    public TerraformAnalyzerSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TerraformAnalyzerSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    static Set<String> getDefaultProfile() {
        if (defaultProfile == null) {
            RuleRegistry registry = new RuleRegistry();
            defaultProfile = Set.copyOf(registry.getQualimetryTerraformRuleKeys());
        }
        return defaultProfile;
    }

    public boolean isRuleEnabled(String ruleKey) {
        RuleOverride override = rules.get(ruleKey);
        if (override != null) {
            return override.enabled;
        }
        if (rulesReplaceDefaults) {
            return false;
        }
        return getDefaultProfile().contains(ruleKey);
    }

    public String getRuleSeverity(String ruleKey) {
        RuleOverride override = rules.get(ruleKey);
        if (override != null && override.severity != null && !override.severity.isBlank()) {
            return override.severity;
        }
        return null;
    }

    public static class RuleOverride {
        public boolean enabled = true;
        public String severity;

        @SuppressWarnings("unused")
        public RuleOverride() {
        }

        public RuleOverride(boolean enabled, String severity) {
            this.enabled = enabled;
            this.severity = severity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RuleOverride that = (RuleOverride) o;
            return enabled == that.enabled && java.util.Objects.equals(severity, that.severity);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(enabled, severity);
        }
    }
}
