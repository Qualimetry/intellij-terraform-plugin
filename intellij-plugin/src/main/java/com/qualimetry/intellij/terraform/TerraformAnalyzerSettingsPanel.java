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

import com.intellij.util.ui.FormBuilder;
import com.qualimetry.terraform.rules.Rule;
import com.qualimetry.terraform.rules.RuleRegistry;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Settings panel for the Qualimetry Terraform Analyzer plugin.
 * Provides per-rule enable/disable, severity override, and filter.
 */
final class TerraformAnalyzerSettingsPanel {

    private static final String[] SEVERITY_OPTIONS = {"default", "blocker", "critical", "major", "minor", "info"};

    private final JCheckBox enabledCheckBox;
    private final JCheckBox rulesReplaceDefaultsCheckBox;
    private final JTextField filterField;
    private final JTable rulesTable;
    private final DefaultTableModel tableModel;
    private final JPanel mainPanel;

    private List<RuleRow> allRows;
    private Set<String> defaultRuleKeys;

    TerraformAnalyzerSettingsPanel() {
        enabledCheckBox = new JCheckBox("Enable Qualimetry Terraform Analyzer");
        enabledCheckBox.setSelected(true);

        rulesReplaceDefaultsCheckBox = new JCheckBox("Only run rules listed below (e.g. after SonarQube import)");
        rulesReplaceDefaultsCheckBox.setSelected(false);

        filterField = new JTextField(25);
        filterField.setToolTipText("Filter rules by key or description");

        String[] columnNames = {"Enabled", "Rule key", "Description", "Severity", "In default profile"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return Boolean.class;
                if (column == 4) return Boolean.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3;
            }
        };
        rulesTable = new JTable(tableModel);
        rulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rulesTable.setRowHeight(22);
        TableColumnModel cm = rulesTable.getColumnModel();
        if (cm.getColumnCount() >= 5) {
            cm.getColumn(0).setPreferredWidth(60);
            cm.getColumn(1).setPreferredWidth(180);
            cm.getColumn(2).setPreferredWidth(220);
            cm.getColumn(3).setPreferredWidth(90);
            cm.getColumn(4).setPreferredWidth(120);
        }
        rulesTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JComboBox<>(SEVERITY_OPTIONS)));
        JScrollPane scrollPane = new JScrollPane(rulesTable);

        JButton resetButton = new JButton("Reset to Defaults");
        resetButton.addActionListener(e -> resetToDefaults());

        defaultRuleKeys = TerraformAnalyzerSettings.getDefaultProfile();
        allRows = buildRuleRows();
        applyFilter("");

        JPanel topPanel = FormBuilder.createFormBuilder()
                .addComponent(enabledCheckBox)
                .addComponent(rulesReplaceDefaultsCheckBox)
                .addSeparator()
                .addComponent(new JLabel("Rules"))
                .addLabeledComponent("Filter:", filterField)
                .addComponent(new JLabel("(Enabled, Rule key, Description, Severity, In default profile)"))
                .getPanel();

        filterField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                applyFilter(filterField.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                applyFilter(filterField.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                applyFilter(filterField.getText());
            }
        });

        mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(resetButton, BorderLayout.SOUTH);
    }

    private List<RuleRow> buildRuleRows() {
        RuleRegistry registry = new RuleRegistry();
        List<RuleRow> rows = new ArrayList<>();
        for (Rule rule : registry.getAllRules()) {
            String key = rule.getId();
            String description = rule.getName() != null ? rule.getName() : key;
            boolean inDefault = defaultRuleKeys.contains(key);
            rows.add(new RuleRow(key, description, inDefault));
        }
        rows.sort((a, b) -> a.key.compareTo(b.key));
        return rows;
    }

    private void applyFilter(String filter) {
        String lower = filter.trim().toLowerCase();
        List<RuleRow> visible = lower.isEmpty()
                ? new ArrayList<>(allRows)
                : allRows.stream()
                .filter(r -> r.key.toLowerCase().contains(lower) || r.description.toLowerCase().contains(lower))
                .collect(Collectors.toList());
        tableModel.setRowCount(0);
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        for (RuleRow r : visible) {
            TerraformAnalyzerSettings.RuleOverride override = settings.rules.get(r.key);
            boolean enabled = override != null ? override.enabled : (!settings.rulesReplaceDefaults && r.inDefault);
            String severity = override != null && override.severity != null && !override.severity.isBlank()
                    ? override.severity.toLowerCase()
                    : "default";
            tableModel.addRow(new Object[]{enabled, r.key, r.description, severity, r.inDefault});
        }
    }

    private void applyFilterWithDefaults(String filter) {
        String lower = filter.trim().toLowerCase();
        List<RuleRow> visible = lower.isEmpty()
                ? new ArrayList<>(allRows)
                : allRows.stream()
                .filter(r -> r.key.toLowerCase().contains(lower) || r.description.toLowerCase().contains(lower))
                .collect(Collectors.toList());
        tableModel.setRowCount(0);
        for (RuleRow r : visible) {
            tableModel.addRow(new Object[]{r.inDefault, r.key, r.description, "default", r.inDefault});
        }
    }

    private void resetToDefaults() {
        rulesReplaceDefaultsCheckBox.setSelected(false);
        applyFilterWithDefaults(filterField.getText());
    }

    JComponent getComponent() {
        return mainPanel;
    }

    boolean isEnabled() {
        return enabledCheckBox.isSelected();
    }

    void setEnabled(boolean enabled) {
        enabledCheckBox.setSelected(enabled);
    }

    boolean isRulesReplaceDefaults() {
        return rulesReplaceDefaultsCheckBox.isSelected();
    }

    void setRulesReplaceDefaults(boolean value) {
        rulesReplaceDefaultsCheckBox.setSelected(value);
    }

    void loadFrom(@NotNull TerraformAnalyzerSettings settings) {
        enabledCheckBox.setSelected(settings.enabled);
        rulesReplaceDefaultsCheckBox.setSelected(settings.rulesReplaceDefaults);
        applyFilter(filterField.getText());
    }

    void saveTo(@NotNull TerraformAnalyzerSettings settings) {
        settings.enabled = enabledCheckBox.isSelected();
        settings.rulesReplaceDefaults = rulesReplaceDefaultsCheckBox.isSelected();
        Map<String, RowState> tableState = new java.util.HashMap<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String key = (String) tableModel.getValueAt(i, 1);
            Boolean enabledObj = (Boolean) tableModel.getValueAt(i, 0);
            String severityObj = (String) tableModel.getValueAt(i, 3);
            boolean enabled = enabledObj != null && enabledObj;
            String severity = (severityObj != null && !severityObj.isBlank() && !"default".equals(severityObj)) ? severityObj : null;
            tableState.put(key, new RowState(enabled, severity));
        }
        Map<String, TerraformAnalyzerSettings.RuleOverride> existingRules = new java.util.HashMap<>(settings.rules);
        Map<String, TerraformAnalyzerSettings.RuleOverride> rules = settings.rules;
        rules.clear();
        if (settings.rulesReplaceDefaults) {
            for (RuleRow r : allRows) {
                RowState state = tableState.get(r.key);
                TerraformAnalyzerSettings.RuleOverride existing = existingRules.get(r.key);
                boolean enabled;
                String severity;
                if (state != null) {
                    enabled = state.enabled;
                    severity = state.severity;
                } else if (existing != null) {
                    enabled = existing.enabled;
                    severity = existing.severity;
                } else {
                    enabled = false;
                    severity = null;
                }
                rules.put(r.key, new TerraformAnalyzerSettings.RuleOverride(enabled, severity));
            }
        } else {
            for (RuleRow r : allRows) {
                RowState state = tableState.get(r.key);
                boolean enabled = state != null ? state.enabled : r.inDefault;
                String severity = state != null ? state.severity : null;
                boolean differsFromDefault = (enabled != r.inDefault) || (severity != null);
                if (differsFromDefault) {
                    rules.put(r.key, new TerraformAnalyzerSettings.RuleOverride(enabled, severity));
                }
            }
        }
    }

    private static final class RowState {
        final boolean enabled;
        final String severity;

        RowState(boolean enabled, String severity) {
            this.enabled = enabled;
            this.severity = severity;
        }
    }

    private static final class RuleRow {
        final String key;
        final String description;
        final boolean inDefault;

        RuleRow(String key, String description, boolean inDefault) {
            this.key = key;
            this.description = description;
            this.inDefault = inDefault;
        }
    }
}
