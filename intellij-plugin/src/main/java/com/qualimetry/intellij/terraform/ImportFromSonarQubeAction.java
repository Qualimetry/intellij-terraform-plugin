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

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Imports active Terraform rules from a SonarQube quality profile into the plugin settings.
 */
public final class ImportFromSonarQubeAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ImportFromSonarQubeDialog dialog = new ImportFromSonarQubeDialog();
        if (!dialog.showAndGet()) {
            return;
        }
        String serverUrl = dialog.getServerUrl();
        if (serverUrl.isEmpty()) {
            showNotification(e.getProject(), "Server URL is required.", NotificationType.ERROR);
            return;
        }
        String token = dialog.getToken();
        String profileNameOrKey = dialog.getProfileNameOrKey();

        Project project = e.getProject();
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Importing from SonarQube", true) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try {
                    SonarQubeImportService service = new SonarQubeImportService(serverUrl, token);
                    int count = service.importToSettings(profileNameOrKey);
                    TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
                    settings.sonarQubeUrl = serverUrl;
                    settings.sonarQubeProfile = profileNameOrKey;
                    ApplicationManager.getApplication().invokeLater(() ->
                            showNotification(project, "Imported " + count + " rule(s) from SonarQube.", NotificationType.INFORMATION));
                } catch (IOException | InterruptedException ex) {
                    String message = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
                    ApplicationManager.getApplication().invokeLater(() ->
                            showNotification(project, "Import failed: " + message, NotificationType.ERROR));
                }
            }
        });
    }

    private static void showNotification(Project project, String content, NotificationType type) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("Qualimetry Terraform")
                .createNotification(content, type)
                .notify(project);
    }
}
