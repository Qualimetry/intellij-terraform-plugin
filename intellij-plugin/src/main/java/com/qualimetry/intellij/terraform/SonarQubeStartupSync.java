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

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.ide.AppLifecycleListener;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Re-imports rules from the last-used SonarQube server and profile on IDE startup,
 * so quality-profile changes propagate without a manual re-import.
 */
public final class SonarQubeStartupSync implements AppLifecycleListener {

    private static final AtomicBoolean SYNC_STARTED = new AtomicBoolean(false);

    @Override
    public void appFrameCreated(@NotNull List<String> commandLineArgs) {
        if (!SYNC_STARTED.compareAndSet(false, true)) {
            return;
        }
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        if (!settings.enabled || !settings.autoSyncOnStartup
                || settings.sonarQubeUrl == null || settings.sonarQubeUrl.isBlank()) {
            return;
        }
        String serverUrl = settings.sonarQubeUrl;
        String profile = settings.sonarQubeProfile;
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                String token = SonarQubeTokenStore.load();
                new SonarQubeImportService(serverUrl, token).importToSettings(profile);
                ApplicationManager.getApplication().invokeLater(() -> {
                    for (Project project : ProjectManager.getInstance().getOpenProjects()) {
                        if (!project.isDisposed()) {
                            DaemonCodeAnalyzer.getInstance(project).settingsChanged();
                        }
                    }
                });
            } catch (Exception ex) {
                String message = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
                ApplicationManager.getApplication().invokeLater(() ->
                        NotificationGroupManager.getInstance()
                                .getNotificationGroup("Qualimetry Terraform")
                                .createNotification(
                                        "SonarQube rule sync failed: " + message + ". Using previously imported rules.",
                                        NotificationType.WARNING)
                                .notify(null));
            }
        });
    }
}
