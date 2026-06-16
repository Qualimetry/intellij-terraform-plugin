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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qualimetry.terraform.rules.RuleRegistry;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Fetches Terraform quality profile and active rules from SonarQube API
 * and applies them to {@link TerraformAnalyzerSettings}.
 */
final class SonarQubeImportService {

    private static final String REPO_PREFIX = RuleRegistry.REPOSITORY_KEY + ":";
    private static final String LANGUAGE = "terraform";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final String baseUrl;
    private final String token;
    private final HttpClient client;

    SonarQubeImportService(String serverUrl, String token) {
        this.baseUrl = normalizeUrl(serverUrl);
        this.token = token == null ? "" : token.trim();
        this.client = HttpClient.newBuilder()
                .connectTimeout(REQUEST_TIMEOUT)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    static String normalizeUrl(String url) {
        String u = url == null ? "" : url.trim();
        if (!u.isEmpty() && !Pattern.compile("^https?://", Pattern.CASE_INSENSITIVE).matcher(u).find()) {
            u = "https://" + u;
        }
        return u.replaceAll("/+$", "");
    }

    /**
     * @return number of rules imported
     */
    int importToSettings(String profileNameOrKey) throws IOException, InterruptedException {
        String profileKey = resolveProfileKey(profileNameOrKey);
        Map<String, TerraformAnalyzerSettings.RuleOverride> rules = fetchActiveRules(profileKey);
        Set<String> defaultKeys = TerraformAnalyzerSettings.getDefaultProfile();
        TerraformAnalyzerSettings settings = TerraformAnalyzerSettings.getInstance();
        settings.rulesReplaceDefaults = true;
        settings.rules.clear();
        for (Map.Entry<String, TerraformAnalyzerSettings.RuleOverride> e : rules.entrySet()) {
            settings.rules.put(e.getKey(), e.getValue());
        }
        for (String key : defaultKeys) {
            if (!settings.rules.containsKey(key)) {
                settings.rules.put(key, new TerraformAnalyzerSettings.RuleOverride(false, null));
            }
        }
        return rules.size();
    }

    private String resolveProfileKey(String profileNameOrKey) throws IOException, InterruptedException {
        String url = baseUrl + "/api/qualityprofiles/search?language=" + LANGUAGE;
        HttpRequest request = newRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() != 200) {
            throw new IOException("Profiles request failed: HTTP " + response.statusCode() + " from " + url);
        }
        JsonArray profiles = getArray(parseObject(response.body()), "profiles");
        if (profiles.isEmpty()) {
            throw new IOException("No Terraform quality profiles found on the server.");
        }
        String input = (profileNameOrKey == null ? "" : profileNameOrKey).trim().toLowerCase(Locale.ROOT);
        if (!input.isEmpty()) {
            for (JsonElement element : profiles) {
                JsonObject profile = element.getAsJsonObject();
                String key = getString(profile, "key");
                String name = getString(profile, "name");
                if (input.equals(key == null ? null : key.toLowerCase(Locale.ROOT))
                        || input.equals(name == null ? null : name.toLowerCase(Locale.ROOT))) {
                    return key;
                }
            }
            for (JsonElement element : profiles) {
                JsonObject profile = element.getAsJsonObject();
                String key = getString(profile, "key");
                String name = getString(profile, "name");
                if ((key != null && key.toLowerCase(Locale.ROOT).contains(input))
                        || (name != null && name.toLowerCase(Locale.ROOT).contains(input))) {
                    return key;
                }
            }
        }
        return getString(profiles.get(0).getAsJsonObject(), "key");
    }

    private Map<String, TerraformAnalyzerSettings.RuleOverride> fetchActiveRules(String profileKey) throws IOException, InterruptedException {
        Map<String, TerraformAnalyzerSettings.RuleOverride> result = new LinkedHashMap<>();
        int page = 1;
        int pageSize = 100;
        while (true) {
            String url = baseUrl + "/api/rules/search?activation=true&qprofile=" + URLEncoder.encode(profileKey, StandardCharsets.UTF_8) + "&f=actives&p=" + page + "&ps=" + pageSize;
            HttpRequest request = newRequest(url);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new IOException("Rules request failed: HTTP " + response.statusCode() + " from " + url);
            }
            JsonObject root = parseObject(response.body());
            int total = root.has("total") && root.get("total").isJsonPrimitive() ? root.get("total").getAsInt() : 0;
            JsonArray rules = getArray(root, "rules");
            JsonObject actives = root.has("actives") && root.get("actives").isJsonObject()
                    ? root.getAsJsonObject("actives")
                    : new JsonObject();
            extractActiveRules(rules, actives, result);
            if (page * pageSize >= total || rules.isEmpty()) {
                break;
            }
            page++;
        }
        return result;
    }

    private void extractActiveRules(JsonArray rules, JsonObject actives,
                                    Map<String, TerraformAnalyzerSettings.RuleOverride> out) {
        for (JsonElement element : rules) {
            if (!element.isJsonObject()) {
                continue;
            }
            JsonObject rule = element.getAsJsonObject();
            String fullKey = getString(rule, "key");
            if (fullKey == null || !fullKey.startsWith(REPO_PREFIX)) {
                continue;
            }
            String ruleKey = fullKey.substring(REPO_PREFIX.length());
            if (ruleKey.isEmpty()) {
                continue;
            }
            JsonObject activation = firstActivation(actives, fullKey);
            String severity = activation != null ? getString(activation, "severity") : null;
            if (severity == null) {
                severity = getString(rule, "severity");
            }
            TerraformAnalyzerSettings.RuleOverride override = new TerraformAnalyzerSettings.RuleOverride(
                    true, severity == null ? null : severity.toLowerCase(Locale.ROOT));
            if (activation != null) {
                for (JsonElement paramElement : getArray(activation, "params")) {
                    if (!paramElement.isJsonObject()) {
                        continue;
                    }
                    JsonObject param = paramElement.getAsJsonObject();
                    String paramKey = getString(param, "key");
                    String paramValue = getString(param, "value");
                    if (paramKey != null && paramValue != null) {
                        override.params.put(paramKey, paramValue);
                    }
                }
            }
            out.put(ruleKey, override);
        }
    }

    private static JsonObject firstActivation(JsonObject actives, String fullKey) {
        if (!actives.has(fullKey) || !actives.get(fullKey).isJsonArray()) {
            return null;
        }
        JsonArray activations = actives.getAsJsonArray(fullKey);
        if (activations.isEmpty() || !activations.get(0).isJsonObject()) {
            return null;
        }
        return activations.get(0).getAsJsonObject();
    }

    private static JsonObject parseObject(String body) throws IOException {
        JsonElement root = JsonParser.parseString(body);
        if (!root.isJsonObject()) {
            throw new IOException("Unexpected response from SonarQube: not a JSON object.");
        }
        return root.getAsJsonObject();
    }

    private static JsonArray getArray(JsonObject obj, String name) {
        return obj.has(name) && obj.get(name).isJsonArray() ? obj.getAsJsonArray(name) : new JsonArray();
    }

    private static String getString(JsonObject obj, String name) {
        return obj.has(name) && obj.get(name).isJsonPrimitive() ? obj.get(name).getAsString() : null;
    }

    private HttpRequest newRequest(String urlString) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(REQUEST_TIMEOUT)
                .header("Accept", "application/json")
                .header("User-Agent", "Qualimetry-Terraform-IntelliJ/1.0");
        if (!token.isEmpty()) {
            builder.header("Authorization", "Bearer " + token);
        }
        return builder.GET().build();
    }
}
