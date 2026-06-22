package com.qualimetry.terraform.rules;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Generates an enterprise-grade evidence report from rule test results, aligned with the
 * Qualimetry Gherkin (and Ansible) plugin report: same HTML structure, CSS variables,
 * section numbering, header/nav, KPI cards, filter bar, and attestation sign-off.
 * <p>
 * Called from ToolResultMapperRuleKeyTest's @AfterAll after the parameterized tests have run—
 * the report is produced from the actual unit test results, not from a separate loop.
 */
public final class EvidenceReportGenerator {

    private static final int EXPECTED_MIN_RULES = 766;

    private EvidenceReportGenerator() {
    }

    public static void generate(Path reportDir, List<RuleEvidenceResult> results) throws IOException {
        Files.createDirectories(reportDir);
        int total = results.size();
        int passed = (int) results.stream().filter(r -> "PASS".equals(r.status())).count();
        int failed = (int) results.stream().filter(r -> "FAIL".equals(r.status())).count();
        double passRate = total > 0 ? (passed * 100.0) / total : 0;

        long tflintCount = results.stream().filter(r -> r.ruleKey().startsWith("qa-tflint-")).count();
        long trivyCount = results.stream().filter(r -> r.ruleKey().startsWith("qa-trivy-")).count();
        long checkovCount = results.stream().filter(r -> r.ruleKey().startsWith("qa-checkov-")).count();

        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"));
        String tsIso = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        StringBuilder h = new StringBuilder(120_000);
        h.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n");
        h.append("<meta charset=\"UTF-8\">\n");
        h.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n");
        h.append("<title>Terraform Rules - Test Evidence Pack</title>\n");
        h.append("<style>\n");
        appendCss(h);
        h.append("</style>\n</head>\n<body>\n");

        // —— HEADER ——
        h.append("<header>\n");
        h.append("  <div class=\"header-inner\">\n");
        h.append("    <div class=\"header-brand\">\n");
        h.append("      <div class=\"logo\">Q</div>\n");
        h.append("      <div>\n");
        h.append("        <h1>Qualimetry Terraform Rules</h1>\n");
        h.append("        <p class=\"header-sub\">Automated Test Verification Evidence Pack</p>\n");
        h.append("      </div>\n");
        h.append("    </div>\n");
        h.append("    <div class=\"header-meta\">\n");
        h.append("      <div class=\"hm-item\"><span class=\"hm-label\">Generated</span><span class=\"hm-value\">").append(esc(ts)).append("</span></div>\n");
        h.append("      <div class=\"hm-item\"><span class=\"hm-label\">Rules</span><span class=\"hm-value\">").append(total).append("</span></div>\n");
        h.append("      <div class=\"hm-item\"><span class=\"hm-label\">Passed</span><span class=\"hm-value\">").append(passed).append("</span></div>\n");
        h.append("      <div class=\"hm-item\"><span class=\"hm-label\">Failed</span><span class=\"hm-value\">").append(failed).append("</span></div>\n");
        h.append("    </div>\n");
        h.append("  </div>\n");
        h.append("</header>\n\n");

        // —— NAV ——
        h.append("<nav id=\"report-nav\"><div class=\"nav-inner\">\n");
        h.append("  <a href=\"#executive-summary\">Summary</a>\n");
        h.append("  <a href=\"#methodology\">Methodology</a>\n");
        h.append("  <a href=\"#rules-index\">Rules Index</a>\n");
        h.append("  <a href=\"#attestation\">Attestation</a>\n");
        h.append("</div></nav>\n\n");

        // —— EXECUTIVE SUMMARY ——
        h.append("<section id=\"executive-summary\">\n");
        h.append("  <h2><span class=\"sec-num\">1</span> Executive Summary</h2>\n");
        h.append("  <div class=\"summary-row\">\n");
        h.append("    <div class=\"donut-card\">\n");
        h.append("      <div class=\"donut\" style=\"--pct:").append(String.format("%.1f", passRate));
        h.append(";--color:var(--trust-green)\">\n");
        h.append("        <div class=\"donut-centre\"><span class=\"donut-value\">");
        h.append(String.format("%.1f", passRate)).append("%</span><span class=\"donut-label\">Pass Rate</span></div>\n");
        h.append("      </div>\n");
        h.append("      <p class=\"donut-caption\">").append(passed).append(" of ").append(total);
        h.append(" rules verified by mapping test</p>\n");
        h.append("    </div>\n");
        h.append("    <div class=\"kpi-grid\">\n");
        kpi(h, String.valueOf(total), "Total Rules", "neutral");
        kpi(h, String.valueOf(passed), "Passed", "green");
        kpi(h, String.valueOf(failed), "Failed", failed > 0 ? "red" : "green");
        kpi(h, String.format("%.1f%%", passRate), "Pass Rate", "blue");
        if (total < EXPECTED_MIN_RULES) {
            kpi(h, "Run content:generate", "Note", "red");
        }
        h.append("    </div>\n");
        h.append("  </div>\n");

        // Source distribution
        h.append("  <div class=\"distrib-panel\">\n");
        h.append("    <h3>Rules by Source</h3>\n");
        h.append("    <div class=\"bar-chart\">\n");
        distribBar(h, "tflint", tflintCount, total, "var(--teal)");
        distribBar(h, "trivy", trivyCount, total, "var(--blue)");
        distribBar(h, "checkov", checkovCount, total, "var(--purple)");
        h.append("    </div>\n");
        h.append("  </div>\n");
        if (total < EXPECTED_MIN_RULES) {
            h.append("  <p class=\"section-note\"><strong>Note:</strong> Expected at least ").append(EXPECTED_MIN_RULES);
            h.append(" rules. Run <code>npm run content:generate</code> and rebuild to refresh rules.json.</p>\n");
        }
        h.append("</section>\n\n");

        // —— METHODOLOGY ——
        h.append("<section id=\"methodology\">\n");
        h.append("  <h2><span class=\"sec-num\">2</span> Verification Methodology</h2>\n");
        h.append("  <div class=\"method-grid\">\n");
        methodCard(h, "Synthetic Tool Output",
                "For each rule (tflint, Trivy, Checkov), the test builds minimal synthetic JSON in the tool's native format, including the rule's tool ID and a single finding (file, line, message).");
        methodCard(h, "Parser and Mapper",
                "The JSON is parsed with the corresponding parser (<code>TflintOutputParser</code>, <code>TrivyOutputParser</code>, or <code>CheckovOutputParser</code>) and mapped via <code>ToolResultMapper</code> with <code>RuleRegistry</code>.");
        methodCard(h, "Assertion",
                "The test asserts exactly one <code>MappedFinding</code> with the expected rule key, file, line, message, and non-null severity and type. Any mismatch is recorded as FAIL in the evidence list.");
        methodCard(h, "Report from Tests",
                "This report is generated in <code>ToolResultMapperRuleKeyTest</code>'s <code>@AfterAll</code> from the collected results. Each of the " + total + " rule-key test invocations records PASS or FAIL; no separate loop produces the report.");
        h.append("  </div>\n");
        h.append("  <p class=\"section-intro\">Evidence for each rule is recorded under <code>rules/&lt;ruleKey&gt;/results.json</code> (status PASS/FAIL and optional message).</p>\n");
        h.append("</section>\n\n");

        // —— RULES INDEX ——
        h.append("<section id=\"rules-index\">\n");
        h.append("  <h2><span class=\"sec-num\">3</span> Rules Index</h2>\n");
        h.append("  <div class=\"filter-bar\">\n");
        h.append("    <input type=\"text\" id=\"rule-search\" placeholder=\"Search rules...\" onkeyup=\"filterRules()\">\n");
        h.append("    <div class=\"filter-group\">\n");
        h.append("      <button class=\"fbtn active\" onclick=\"filterSource(this,'all')\">All</button>\n");
        h.append("      <button class=\"fbtn\" onclick=\"filterSource(this,'tflint')\">tflint</button>\n");
        h.append("      <button class=\"fbtn\" onclick=\"filterSource(this,'trivy')\">trivy</button>\n");
        h.append("      <button class=\"fbtn\" onclick=\"filterSource(this,'checkov')\">checkov</button>\n");
        h.append("    </div>\n");
        h.append("  </div>\n");
        h.append("  <table class=\"rules-table\" id=\"rules-table\">\n");
        h.append("    <thead><tr><th>#</th><th>Rule Key</th><th>Source</th><th>Status</th><th>Message</th></tr></thead>\n");
        h.append("    <tbody>\n");
        int idx = 0;
        for (RuleEvidenceResult r : results) {
            idx++;
            String source = sourceFromRuleKey(r.ruleKey());
            String sc = "FAIL".equals(r.status()) ? "fail" : "pass";
            String searchText = (r.ruleKey() + " " + source).toLowerCase();
            String ruleDir = "rules/" + sanitize(r.ruleKey());
            String message = r.message() != null && !r.message().isEmpty() ? esc(r.message()) : "";
            h.append("    <tr data-source=\"").append(esc(source)).append("\" data-search=\"").append(esc(searchText)).append("\" class=\"row-").append(sc).append("\">");
            h.append("<td class=\"num\">").append(idx).append("</td>");
            h.append("<td><a href=\"").append(esc(ruleDir)).append("/results.json\" class=\"rule-link\">").append(esc(r.ruleKey())).append("</a></td>");
            h.append("<td class=\"cat\">").append(esc(source)).append("</td>");
            h.append("<td><span class=\"pill pill-").append(sc).append("\">").append(esc(r.status())).append("</span></td>");
            h.append("<td class=\"msg\">").append(message).append("</td>");
            h.append("</tr>\n");
        }
        h.append("    </tbody>\n  </table>\n");
        h.append("</section>\n\n");

        // —— ATTESTATION ——
        h.append("<section id=\"attestation\">\n");
        h.append("  <h2><span class=\"sec-num\">4</span> Reviewer Attestation</h2>\n");
        h.append("  <div class=\"attestation-box\">\n");
        h.append("    <p>This evidence pack was generated automatically on <strong>").append(esc(ts)).append("</strong> by the Terraform Rules test suite. All ").append(total);
        h.append(" rules were exercised with synthetic tool output; ").append(passed).append(" mapping verifications passed");
        if (failed > 0) {
            h.append(", ").append(failed).append(" failed");
        }
        h.append(".</p>\n");
        h.append("    <p>The evidence demonstrates that each rule key:</p>\n");
        h.append("    <ol>\n");
        h.append("      <li>Is covered by a parameterized unit test (one invocation per rule)</li>\n");
        h.append("      <li>Receives synthetic tool JSON and is parsed and mapped correctly</li>\n");
        h.append("      <li>Produces exactly one MappedFinding with the expected rule key, file, line, and message</li>\n");
        h.append("    </ol>\n");
        h.append("    <div class=\"sign-off\">\n");
        h.append("      <div class=\"sign-field\"><span class=\"sign-label\">Reviewed by</span><span class=\"sign-line\"></span></div>\n");
        h.append("      <div class=\"sign-field\"><span class=\"sign-label\">Date</span><span class=\"sign-line\"></span></div>\n");
        h.append("      <div class=\"sign-field\"><span class=\"sign-label\">Disposition</span><span class=\"sign-line\"></span></div>\n");
        h.append("    </div>\n");
        h.append("  </div>\n");
        h.append("</section>\n\n");

        // —— FOOTER ——
        h.append("<footer>\n");
        h.append("  <p>Qualimetry Terraform Rules &mdash; Automated Test Verification Evidence Pack</p>\n");
        h.append("  <p>Report generated <time datetime=\"").append(esc(tsIso)).append("\">").append(esc(ts)).append("</time>");
        h.append(" &bull; Self-contained document &mdash; no external dependencies</p>\n");
        h.append("</footer>\n\n");

        h.append("<script>\n");
        appendJs(h);
        h.append("</script>\n");
        h.append("</body>\n</html>\n");

        Files.writeString(reportDir.resolve("index.html"), h.toString(), StandardCharsets.UTF_8);

        for (RuleEvidenceResult r : results) {
            Path ruleDir = reportDir.resolve("rules").resolve(sanitize(r.ruleKey()));
            Files.createDirectories(ruleDir);
            String resultsJson = "{\"ruleKey\":\"" + escapeJson(r.ruleKey()) + "\",\"status\":\"" + r.status() + "\""
                    + (r.message() != null ? ",\"message\":\"" + escapeJson(r.message()) + "\"" : "")
                    + "}";
            Files.writeString(ruleDir.resolve("results.json"), resultsJson, StandardCharsets.UTF_8);
        }
    }

    private static void kpi(StringBuilder h, String value, String label, String tone) {
        h.append("      <div class=\"kpi kpi-").append(tone).append("\">");
        h.append("<span class=\"kpi-val\">").append(esc(value)).append("</span>");
        h.append("<span class=\"kpi-lbl\">").append(esc(label)).append("</span></div>\n");
    }

    private static void distribBar(StringBuilder h, String label, long count, long total, String color) {
        double pct = total > 0 ? (count * 100.0) / total : 0;
        h.append("      <div class=\"bar-row\"><span class=\"bar-label\">").append(esc(label));
        h.append("</span><div class=\"bar-track\"><div class=\"bar-fill\" style=\"width:");
        h.append(String.format("%.1f", pct)).append("%;background:").append(color);
        h.append("\"></div></div><span class=\"bar-count\">").append(count).append("</span></div>\n");
    }

    private static void methodCard(StringBuilder h, String title, String body) {
        h.append("    <div class=\"mcard\"><h4>").append(esc(title)).append("</h4><p>").append(body).append("</p></div>\n");
    }

    private static void appendCss(StringBuilder h) {
        h.append("""
            :root {
                --navy: #0f2b46; --navy-light: #183d5d;
                --trust-green: #0a7c42; --trust-green-light: #e7f5ee;
                --alert-red: #b71c1c; --alert-red-light: #fce4ec;
                --amber: #e68a00; --amber-light: #fff8e1;
                --blue: #1565c0; --blue-light: #e3f2fd;
                --purple: #6a1b9a; --purple-light: #f3e5f5;
                --teal: #00695c; --teal-light: #e0f2f1;
                --slate: #37474f; --slate-light: #eceff1;
                --bg: #f5f6fa; --bg-card: #ffffff;
                --border: #dde1e6; --border-light: #eef0f4;
                --text: #1a202c; --text-2: #4a5568; --text-3: #a0aec0;
                --mono: 'Cascadia Code','Fira Code','JetBrains Mono','Consolas',monospace;
                --sans: -apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,Oxygen,Ubuntu,Cantarell,sans-serif;
                --radius: 8px; --shadow: 0 1px 3px rgba(0,0,0,.08),0 1px 2px rgba(0,0,0,.06);
            }
            *, *::before, *::after { margin:0; padding:0; box-sizing:border-box; }
            body { font-family:var(--sans); color:var(--text); background:var(--bg); line-height:1.65; font-size:15px; }

            header { background:var(--navy); color:#fff; padding:0; }
            .header-inner { max-width:1280px; margin:0 auto; padding:1.5rem 2rem; display:flex; align-items:center; justify-content:space-between; flex-wrap:wrap; gap:1rem; }
            .header-brand { display:flex; align-items:center; gap:1rem; }
            .logo { width:48px; height:48px; background:var(--trust-green); border-radius:10px; display:flex; align-items:center; justify-content:center; font-size:1.6rem; font-weight:800; color:#fff; }
            header h1 { font-size:1.35rem; font-weight:700; letter-spacing:-.02em; }
            .header-sub { font-size:.82rem; opacity:.75; margin-top:2px; }
            .header-meta { display:flex; gap:1.5rem; flex-wrap:wrap; }
            .hm-item { display:flex; flex-direction:column; align-items:flex-end; }
            .hm-label { font-size:.7rem; text-transform:uppercase; letter-spacing:.06em; opacity:.6; }
            .hm-value { font-size:.95rem; font-weight:600; }

            #report-nav { background:var(--navy-light); position:sticky; top:0; z-index:100; box-shadow:var(--shadow); }
            .nav-inner { max-width:1280px; margin:0 auto; padding:0 2rem; display:flex; gap:.25rem; }
            .nav-inner a { color:rgba(255,255,255,.8); text-decoration:none; padding:.65rem 1rem; font-size:.82rem; font-weight:500; border-bottom:2px solid transparent; transition:all .15s; }
            .nav-inner a:hover { color:#fff; border-bottom-color:var(--trust-green); background:rgba(255,255,255,.05); }

            section { max-width:1280px; margin:2rem auto; padding:0 2rem; }
            section h2 { font-size:1.25rem; font-weight:700; color:var(--navy); margin-bottom:1.25rem; padding-bottom:.5rem; border-bottom:2px solid var(--navy); display:flex; align-items:center; gap:.65rem; }
            .sec-num { display:inline-flex; align-items:center; justify-content:center; width:28px; height:28px; background:var(--navy); color:#fff; border-radius:50%; font-size:.78rem; font-weight:700; flex-shrink:0; }
            .section-intro { color:var(--text-2); margin-bottom:1.25rem; font-size:.92rem; }
            .section-note { color:var(--alert-red); font-size:.9rem; margin-top:.75rem; }

            .summary-row { display:flex; gap:1.5rem; flex-wrap:wrap; margin-bottom:1.5rem; }
            .donut-card { background:var(--bg-card); border-radius:var(--radius); box-shadow:var(--shadow); padding:1.5rem; text-align:center; min-width:220px; }
            .donut { width:160px; height:160px; border-radius:50%; background:conic-gradient(var(--color) calc(var(--pct)*1%),var(--border-light) 0); display:flex; align-items:center; justify-content:center; margin:0 auto 1rem; }
            .donut-centre { width:120px; height:120px; border-radius:50%; background:var(--bg-card); display:flex; flex-direction:column; align-items:center; justify-content:center; }
            .donut-value { font-size:1.75rem; font-weight:800; color:var(--navy); }
            .donut-label { font-size:.72rem; text-transform:uppercase; letter-spacing:.05em; color:var(--text-3); }
            .donut-caption { font-size:.82rem; color:var(--text-2); }
            .kpi-grid { display:grid; grid-template-columns:repeat(3,1fr); gap:.75rem; flex:1; min-width:300px; }
            .kpi { background:var(--bg-card); border-radius:var(--radius); box-shadow:var(--shadow); padding:1rem; text-align:center; border-top:3px solid var(--border); }
            .kpi-green { border-top-color:var(--trust-green); }
            .kpi-red { border-top-color:var(--alert-red); }
            .kpi-blue { border-top-color:var(--blue); }
            .kpi-val { display:block; font-size:1.5rem; font-weight:800; color:var(--navy); }
            .kpi-lbl { display:block; font-size:.75rem; color:var(--text-2); text-transform:uppercase; letter-spacing:.04em; margin-top:2px; }
            .kpi-green .kpi-val { color:var(--trust-green); }
            .kpi-red .kpi-val { color:var(--alert-red); }

            .distrib-panel { background:var(--bg-card); border-radius:var(--radius); box-shadow:var(--shadow); padding:1.25rem; margin-bottom:1rem; }
            .distrib-panel h3 { font-size:.9rem; font-weight:600; color:var(--navy); margin-bottom:.75rem; }
            .bar-chart { display:flex; flex-direction:column; gap:.5rem; }
            .bar-row { display:flex; align-items:center; gap:.75rem; }
            .bar-label { width:70px; font-size:.78rem; font-weight:600; text-align:right; color:var(--text-2); }
            .bar-track { flex:1; height:22px; background:var(--border-light); border-radius:4px; overflow:hidden; }
            .bar-fill { height:100%; border-radius:4px; transition:width .4s; min-width:2px; }
            .bar-count { width:30px; font-size:.82rem; font-weight:600; color:var(--text-2); }

            .pill { display:inline-block; padding:.15rem .55rem; border-radius:4px; font-size:.72rem; font-weight:700; text-transform:uppercase; letter-spacing:.03em; white-space:nowrap; }
            .pill-pass { background:var(--trust-green-light); color:var(--trust-green); }
            .pill-fail { background:var(--alert-red-light); color:var(--alert-red); }

            .method-grid { display:grid; grid-template-columns:repeat(auto-fit,minmax(260px,1fr)); gap:1rem; margin-bottom:1.25rem; }
            .mcard { background:var(--bg-card); border-radius:var(--radius); box-shadow:var(--shadow); padding:1.25rem; border-left:4px solid var(--navy); }
            .mcard h4 { font-size:.9rem; color:var(--navy); margin-bottom:.4rem; }
            .mcard p { font-size:.84rem; color:var(--text-2); line-height:1.55; }
            .mcard code { font-family:var(--mono); font-size:.78rem; background:var(--slate-light); padding:.1rem .3rem; border-radius:3px; }

            .filter-bar { display:flex; gap:.75rem; align-items:center; margin-bottom:1rem; flex-wrap:wrap; }
            #rule-search { flex:1; min-width:200px; padding:.5rem .75rem; border:1px solid var(--border); border-radius:6px; font-size:.88rem; outline:none; transition:border .15s; }
            #rule-search:focus { border-color:var(--navy); box-shadow:0 0 0 3px rgba(15,43,70,.1); }
            .filter-group { display:flex; gap:.25rem; }
            .fbtn { padding:.4rem .75rem; border:1px solid var(--border); border-radius:5px; background:var(--bg-card); font-size:.78rem; font-weight:600; cursor:pointer; transition:all .15s; }
            .fbtn:hover { background:var(--slate-light); }
            .fbtn.active { background:var(--navy); color:#fff; border-color:var(--navy); }

            .rules-table { width:100%; border-collapse:collapse; background:var(--bg-card); border-radius:var(--radius); overflow:hidden; box-shadow:var(--shadow); font-size:.85rem; }
            .rules-table th { text-align:left; padding:.55rem .75rem; background:var(--slate-light); font-weight:600; color:var(--slate); border-bottom:2px solid var(--border); white-space:nowrap; }
            .rules-table td { padding:.45rem .75rem; border-bottom:1px solid var(--border-light); }
            .rules-table .num { text-align:center; }
            .rules-table .cat { font-size:.78rem; color:var(--text-2); }
            .rules-table .msg { font-family:var(--mono); font-size:.78rem; word-break:break-word; }
            .rules-table tr:hover { background:rgba(15,43,70,.03); }
            .rules-table tr[style*="display:none"] { display:none; }
            .rule-link { color:var(--navy); text-decoration:none; font-family:var(--mono); font-size:.82rem; }
            .rule-link:hover { text-decoration:underline; }

            .attestation-box { background:var(--bg-card); border-radius:var(--radius); box-shadow:var(--shadow); padding:1.5rem 2rem; border-left:4px solid var(--navy); }
            .attestation-box p { margin-bottom:.75rem; font-size:.92rem; color:var(--text-2); }
            .attestation-box ol { margin:.75rem 0 1.5rem 1.5rem; font-size:.9rem; color:var(--text-2); }
            .attestation-box ol li { margin-bottom:.25rem; }
            .attestation-box code { font-family:var(--mono); font-size:.82rem; background:var(--slate-light); padding:.1rem .3rem; border-radius:3px; }
            .sign-off { display:flex; gap:2rem; flex-wrap:wrap; padding-top:1rem; border-top:1px solid var(--border); }
            .sign-field { display:flex; flex-direction:column; gap:.35rem; min-width:200px; flex:1; }
            .sign-label { font-size:.75rem; text-transform:uppercase; letter-spacing:.05em; color:var(--text-3); font-weight:600; }
            .sign-line { border-bottom:1px solid var(--text-3); height:1.8rem; }

            footer { max-width:1280px; margin:2rem auto; padding:1.5rem 2rem; text-align:center; color:var(--text-3); font-size:.8rem; border-top:1px solid var(--border); }

            @media print { body { background:#fff; font-size:12px; } #report-nav { display:none; } .filter-bar { display:none; } section { padding:0 1rem; } }
            @media (max-width:768px) { .header-inner { flex-direction:column; align-items:flex-start; } .summary-row { flex-direction:column; } .kpi-grid { grid-template-columns:repeat(2,1fr); } }
            """);
    }

    private static void appendJs(StringBuilder h) {
        h.append("""
            var activeSource='all';
            function filterSource(btn,src){
                activeSource=src;
                document.querySelectorAll('.filter-group .fbtn').forEach(function(b){b.classList.remove('active')});
                btn.classList.add('active');
                filterRules();
            }
            function filterRules(){
                var q=document.getElementById('rule-search').value.toLowerCase();
                document.querySelectorAll('#rules-table tbody tr').forEach(function(tr){
                    var matchSource=activeSource==='all'||tr.dataset.source===activeSource;
                    var matchText=!q||(tr.dataset.search&&tr.dataset.search.indexOf(q)>=0);
                    tr.style.display=matchSource&&matchText?'':'none';
                });
            }
            """);
    }

    private static String sourceFromRuleKey(String ruleKey) {
        if (ruleKey.startsWith("qa-tflint-")) return "tflint";
        if (ruleKey.startsWith("qa-trivy-")) return "trivy";
        if (ruleKey.startsWith("qa-checkov-")) return "checkov";
        return "—";
    }

    private static String sanitize(String ruleKey) {
        return ruleKey.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    public record RuleEvidenceResult(String ruleKey, String status, String message) {
        public static RuleEvidenceResult pass(String ruleKey) {
            return new RuleEvidenceResult(ruleKey, "PASS", null);
        }
        public static RuleEvidenceResult fail(String ruleKey, String message) {
            return new RuleEvidenceResult(ruleKey, "FAIL", message);
        }
    }
}
