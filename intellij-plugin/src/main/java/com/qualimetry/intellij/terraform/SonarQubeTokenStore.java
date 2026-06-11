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

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Stores the SonarQube token in the IDE credential store (PasswordSafe).
 */
final class SonarQubeTokenStore {

    private SonarQubeTokenStore() {
    }

    private static CredentialAttributes attributes() {
        return new CredentialAttributes(
                CredentialAttributesKt.generateServiceName("Qualimetry Terraform Analyzer", "SonarQube"));
    }

    static void store(@Nullable String token) {
        if (token == null || token.isBlank()) {
            PasswordSafe.getInstance().set(attributes(), null);
        } else {
            PasswordSafe.getInstance().set(attributes(), new Credentials("token", token.trim()));
        }
    }

    @Nullable
    static String load() {
        return PasswordSafe.getInstance().getPassword(attributes());
    }
}
