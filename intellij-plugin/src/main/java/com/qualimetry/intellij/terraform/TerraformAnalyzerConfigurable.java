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

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Settings UI under Settings &gt; Tools &gt; Qualimetry Terraform Analyzer.
 */
public final class TerraformAnalyzerConfigurable implements Configurable {

    private TerraformAnalyzerSettingsPanel panel;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Qualimetry Terraform Analyzer";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        panel = new TerraformAnalyzerSettingsPanel();
        return panel.getComponent();
    }

    @Override
    public boolean isModified() {
        if (panel == null) return false;
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        if (panel.isEnabled() != settings.enabled) return true;
        if (panel.isRulesReplaceDefaults() != settings.rulesReplaceDefaults) return true;
        TerraformAnalyzerSettings temp = new TerraformAnalyzerSettings();
        temp.enabled = panel.isEnabled();
        temp.rulesReplaceDefaults = panel.isRulesReplaceDefaults();
        temp.rules = new java.util.HashMap<>();
        panel.saveTo(temp);
        return !temp.rules.equals(settings.rules);
    }

    @Override
    public void apply() {
        if (panel == null) return;
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        panel.saveTo(settings);
    }

    @Override
    public void reset() {
        if (panel == null) return;
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        panel.loadFrom(settings);
    }

    @Override
    public void disposeUIResources() {
        panel = null;
    }
}
