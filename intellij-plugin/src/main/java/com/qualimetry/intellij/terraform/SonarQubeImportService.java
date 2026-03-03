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
        JsonParser parser = new JsonParser(response.body());
        String[] keys = parser.getStringArray("profiles", "key");
        String[] names = parser.getStringArray("profiles", "name");
        if (keys == null || keys.length == 0) {
            throw new IOException("No Terraform quality profiles found on the server.");
        }
        String input = (profileNameOrKey == null ? "" : profileNameOrKey).trim().toLowerCase();
        if (!input.isEmpty()) {
            for (int i = 0; i < keys.length; i++) {
                String k = keys[i];
                String n = i < names.length ? names[i] : "";
                if (k != null && k.equals(profileNameOrKey.trim())) return k;
                if (k != null && k.toLowerCase().contains(input)) return k;
                if (n != null && n.toLowerCase().equals(input)) return k;
                if (n != null && n.toLowerCase().contains(input)) return k;
            }
        }
        return keys[0];
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
            String body = response.body();
            JsonParser parser = new JsonParser(body);
            int total = parser.getInt("total", 0);
            extractRuleKeysFromRulesResponse(body, result);
            if (page * pageSize >= total) break;
            page++;
        }
        return result;
    }

    private void extractRuleKeysFromRulesResponse(String body, Map<String, TerraformAnalyzerSettings.RuleOverride> out) {
        int i = 0;
        while ((i = body.indexOf(REPO_PREFIX, i)) >= 0) {
            int start = i + REPO_PREFIX.length();
            int end = body.indexOf("\"", start);
            if (end > start) {
                String ruleKey = body.substring(start, end);
                if (!ruleKey.isEmpty()) {
                    out.put(ruleKey, new TerraformAnalyzerSettings.RuleOverride(true, null));
                }
            }
            i = end > start ? end + 1 : i + 1;
        }
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

    private static class JsonParser {
        private final String json;

        JsonParser(String json) { this.json = json; }

        String[] getStringArray(String arrayName, String fieldName) {
            java.util.List<String> list = new java.util.ArrayList<>();
            int arrStart = json.indexOf("\"" + arrayName + "\"");
            if (arrStart < 0) return null;
            arrStart = json.indexOf("[", arrStart);
            if (arrStart < 0) return null;
            int idx = arrStart;
            while (true) {
                int objStart = json.indexOf("{", idx);
                if (objStart < 0) break;
                int fieldIdx = json.indexOf("\"" + fieldName + "\"", objStart);
                if (fieldIdx < 0) break;
                int colon = json.indexOf(":", fieldIdx);
                if (colon < 0) break;
                int q1 = json.indexOf("\"", colon);
                if (q1 < 0) break;
                int q2 = json.indexOf("\"", q1 + 1);
                if (q2 < 0) break;
                list.add(json.substring(q1 + 1, q2));
                idx = q2 + 1;
                if (idx >= json.length()) break;
            }
            return list.isEmpty() ? null : list.toArray(new String[0]);
        }

        int getInt(String key, int defaultValue) {
            int keyIdx = json.indexOf("\"" + key + "\"");
            if (keyIdx < 0) return defaultValue;
            int colon = json.indexOf(":", keyIdx);
            if (colon < 0) return defaultValue;
            int i = colon + 1;
            while (i < json.length() && (Character.isWhitespace(json.charAt(i)) || json.charAt(i) == ',')) i++;
            if (i >= json.length()) return defaultValue;
            int end = i;
            while (end < json.length() && Character.isDigit(json.charAt(end))) end++;
            if (end == i) return defaultValue;
            try {
                return Integer.parseInt(json.substring(i, end));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }
}
