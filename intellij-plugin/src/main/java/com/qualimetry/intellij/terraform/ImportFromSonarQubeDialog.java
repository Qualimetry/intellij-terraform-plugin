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

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog to collect SonarQube server URL, optional token, and optional profile name
 * for importing active Terraform rules.
 */
final class ImportFromSonarQubeDialog extends DialogWrapper {

    private final JTextField urlField;
    private final JPasswordField tokenField;
    private final JTextField profileField;
    private final boolean hasStoredToken;

    ImportFromSonarQubeDialog() {
        super(true);
        setTitle("Import Rules from SonarQube");
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        String storedToken = SonarQubeTokenStore.load();
        hasStoredToken = storedToken != null && !storedToken.isBlank();
        urlField = new JTextField(40);
        urlField.setToolTipText("SonarQube server URL (e.g. https://sonar.mycompany.com)");
        urlField.setText(settings.sonarQubeUrl != null ? settings.sonarQubeUrl : "");
        tokenField = new JPasswordField(24);
        tokenField.setToolTipText(hasStoredToken
                ? "Token for authentication; the saved token will be used if left blank"
                : "Token for authentication");
        profileField = new JTextField(30);
        profileField.setToolTipText("Profile name or key; if empty, the server's default profile is used");
        profileField.setText(settings.sonarQubeProfile != null ? settings.sonarQubeProfile : "");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints lbl = new GridBagConstraints();
        lbl.anchor = GridBagConstraints.WEST;
        lbl.insets = new Insets(4, 0, 4, 8);
        GridBagConstraints fld = new GridBagConstraints();
        fld.fill = GridBagConstraints.HORIZONTAL;
        fld.weightx = 1.0;
        fld.insets = new Insets(4, 0, 4, 0);

        lbl.gridx = 0; lbl.gridy = 0;
        panel.add(new JLabel("Server URL:"), lbl);
        fld.gridx = 1; fld.gridy = 0;
        panel.add(urlField, fld);

        lbl.gridy = 1;
        panel.add(new JLabel(hasStoredToken ? "Token (saved token available):" : "Token (optional):"), lbl);
        fld.gridy = 1;
        panel.add(tokenField, fld);

        int nextRow = 2;
        if (hasStoredToken) {
            JBLabel hint = new JBLabel("Saved token will be used if left blank", UIUtil.ComponentStyle.SMALL);
            hint.setForeground(UIUtil.getContextHelpForeground());
            GridBagConstraints hintConstraints = new GridBagConstraints();
            hintConstraints.gridx = 1;
            hintConstraints.gridy = nextRow;
            hintConstraints.anchor = GridBagConstraints.WEST;
            hintConstraints.insets = new Insets(0, 0, 4, 0);
            panel.add(hint, hintConstraints);
            nextRow++;
        }

        lbl.gridy = nextRow;
        panel.add(new JLabel("Profile name or key (optional):"), lbl);
        fld.gridy = nextRow;
        panel.add(profileField, fld);

        panel.setPreferredSize(new Dimension(600, hasStoredToken ? 140 : 120));
        return panel;
    }

    String getServerUrl() {
        return urlField.getText() == null ? "" : urlField.getText().trim();
    }

    String getToken() {
        char[] pwd = tokenField.getPassword();
        return pwd == null ? "" : new String(pwd).trim();
    }

    String getProfileNameOrKey() {
        return profileField.getText() == null ? "" : profileField.getText().trim();
    }
}
