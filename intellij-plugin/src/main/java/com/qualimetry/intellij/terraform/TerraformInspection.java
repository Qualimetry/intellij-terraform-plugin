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

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.options.OptPane;
import com.intellij.codeInspection.options.OptRegularComponent;
import com.intellij.codeInspection.options.OptionController;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.qualimetry.terraform.rules.MappedFinding;
import com.qualimetry.terraform.rules.Rule;
import com.qualimetry.terraform.rules.RuleRegistry;
import com.qualimetry.terraform.rules.TflintOutputParser;
import com.qualimetry.terraform.rules.ToolFinding;
import com.qualimetry.terraform.rules.ToolResultMapper;
import com.qualimetry.terraform.rules.TrivyOutputParser;
import com.qualimetry.terraform.rules.CheckovOutputParser;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * IntelliJ inspection that runs tflint, Trivy, and Checkov on Terraform files
 * and maps results to the Qualimetry rule registry.
 */
public final class TerraformInspection extends LocalInspectionTool {

    private static final int TOOL_TIMEOUT_SEC = 120;
    private static final RuleRegistry REGISTRY = new RuleRegistry();
    private static final ToolResultMapper MAPPER = new ToolResultMapper(REGISTRY);

    @SuppressWarnings("WeakerAccess")
    public Map<String, Boolean> inspectionProfileRuleEnabled = new LinkedHashMap<>();

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        PsiFile psiFile = holder.getFile();
        String name = psiFile.getName();
        if (name == null || !(name.endsWith(".tf") || name.endsWith(".tf.json"))) {
            return PsiElementVisitor.EMPTY_VISITOR;
        }

        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        if (!settings.enabled) {
            return PsiElementVisitor.EMPTY_VISITOR;
        }

        return new PsiElementVisitor() {
            @Override
            public void visitFile(@NotNull PsiFile file) {
                runAnalysis(file, holder, settings);
            }
        };
    }

    private void runAnalysis(@NotNull PsiFile psiFile, @NotNull ProblemsHolder holder,
                             @NotNull TerraformAnalyzerSettings settings) {
        Document document = psiFile.getViewProvider().getDocument();
        if (document == null) return;

        VirtualFile vf = psiFile.getVirtualFile();
        if (vf == null || vf.getParent() == null) return;
        String workDir = vf.getParent().getPath();
        String fileName = vf.getName();

        List<MappedFinding> allFindings = new ArrayList<>();
        String tflintJson = runTool(workDir, "tflint", "--format", "json");
        if (tflintJson != null) {
            allFindings.addAll(MAPPER.mapTflint(TflintOutputParser.parse(tflintJson, workDir)));
        }
        String trivyJson = runTool(workDir, "trivy", "config", "-f", "json", ".");
        if (trivyJson != null) {
            trivyJson = extractJson(trivyJson);
            if (trivyJson != null) {
                allFindings.addAll(MAPPER.mapTrivy(TrivyOutputParser.parse(trivyJson, workDir)));
            }
        }
        String checkovJson = runTool(workDir, "checkov", "-d", ".", "--output", "json", "--quiet");
        if (checkovJson != null) {
            checkovJson = extractJson(checkovJson);
            if (checkovJson != null) {
                allFindings.addAll(MAPPER.mapCheckov(CheckovOutputParser.parse(checkovJson, workDir)));
            }
        }

        for (MappedFinding f : allFindings) {
            String findingFile = f.getFile();
            if (findingFile == null) continue;
            String normalizedFinding = findingFile.replace('\\', '/');
            if (!normalizedFinding.equals(fileName) && !normalizedFinding.endsWith("/" + fileName)) {
                continue;
            }
            if (!isRuleEnabled(f.getRuleKey(), settings)) continue;

            ProblemHighlightType highlightType = resolveHighlightType(f.getRuleKey(), f.getSeverity(), settings);
            registerProblem(psiFile, document, holder, f, highlightType);
        }
    }

    private ProblemHighlightType resolveHighlightType(String ruleKey, String defaultSeverity,
                                                       TerraformAnalyzerSettings settings) {
        String overrideSeverity = settings.getRuleSeverity(ruleKey);
        if (overrideSeverity != null) {
            return SeverityMapper.toHighlightType(overrideSeverity);
        }
        return SeverityMapper.toHighlightType(defaultSeverity);
    }

    private void registerProblem(@NotNull PsiFile psiFile, @NotNull Document document,
                                  @NotNull ProblemsHolder holder,
                                  @NotNull MappedFinding finding,
                                  @NotNull ProblemHighlightType highlightType) {
        int line = finding.getLine();
        if (line < 1 || line > document.getLineCount()) return;

        int lineIndex = line - 1;
        int lineStart = document.getLineStartOffset(lineIndex);
        int lineEnd = document.getLineEndOffset(lineIndex);
        String lineText = document.getText(new TextRange(lineStart, lineEnd));

        int trimStart = 0;
        while (trimStart < lineText.length() && Character.isWhitespace(lineText.charAt(trimStart))) {
            trimStart++;
        }
        int trimEnd = lineText.length();
        while (trimEnd > trimStart && Character.isWhitespace(lineText.charAt(trimEnd - 1))) {
            trimEnd--;
        }
        if (trimStart >= trimEnd) return;

        TextRange range = new TextRange(lineStart + trimStart, lineStart + trimEnd);
        String message = "[Qualimetry] " + finding.getMessage();
        holder.registerProblem(psiFile, message, highlightType, range);
    }

    private boolean isRuleEnabled(String ruleKey, @NotNull TerraformAnalyzerSettings settings) {
        if (settings.rules.containsKey(ruleKey)) {
            return settings.isRuleEnabled(ruleKey);
        }
        String bindId = "rule_" + ruleKey;
        if (inspectionProfileRuleEnabled.containsKey(bindId)) {
            return Boolean.TRUE.equals(inspectionProfileRuleEnabled.get(bindId));
        }
        return TerraformAnalyzerSettings.getDefaultProfile().contains(ruleKey);
    }

    @NotNull
    @Override
    public OptPane getOptionsPane() {
        List<OptRegularComponent> components = new ArrayList<>();
        for (Rule rule : REGISTRY.getAllRules()) {
            String key = rule.getId();
            String label = rule.getName() != null && !rule.getName().isBlank() ? rule.getName() : key;
            components.add(OptPane.checkbox("rule_" + key, label));
        }
        return OptPane.pane(components.toArray(new OptRegularComponent[0]));
    }

    @NotNull
    @Override
    public OptionController getOptionController() {
        Set<String> defaultProfile = TerraformAnalyzerSettings.getDefaultProfile();
        return OptionController.of(
                bindId -> {
                    if (!bindId.startsWith("rule_")) return true;
                    String ruleKey = bindId.substring(5);
                    TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
                    if (settings.rules.containsKey(ruleKey)) {
                        return settings.isRuleEnabled(ruleKey);
                    }
                    return inspectionProfileRuleEnabled.getOrDefault(bindId, defaultProfile.contains(ruleKey));
                },
                (bindId, value) -> inspectionProfileRuleEnabled.put(bindId, (Boolean) value)
        );
    }

    private static String runTool(String workDir, String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command)
                    .directory(new File(workDir))
                    .redirectErrorStream(true);
            Process p = pb.start();
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            }
            if (!p.waitFor(TOOL_TIMEOUT_SEC, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                return null;
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static String extractJson(String output) {
        if (output == null || output.isEmpty()) return null;
        int start = output.indexOf('{');
        if (start < 0) return null;
        return output.substring(start);
    }
}
