# Evidence report format (Gherkin/Ansible alignment)

The Terraform plugin evidence report MUST match the **format and style** of the Gherkin and Ansible plugins so all Qualimetry plugins present a consistent deliverable for third-party review.

## Produced from real unit tests (same as Gherkin)

Yes — the evidence report is **legitimately produced from the actual unit tests**, not from a separate loop. In the Gherkin profile there are clear unit tests for each rule; we follow the same pattern:

- **One test invocation per rule:** `ToolResultMapperRuleKeyTest.mapsSyntheticFindingToRuleKey(String ruleKey, ...)` is parameterized over all 766 rule keys (tflint, Trivy, Checkov). Each invocation runs the full pipeline: synthetic JSON → parse → map → assert one MappedFinding with the expected rule key.
- **Evidence recorded per run:** Each invocation adds either `RuleEvidenceResult.pass(ruleKey)` or `RuleEvidenceResult.fail(ruleKey, message)` to a shared `EVIDENCE_RESULTS` list (in the try/catch of the test method).
- **Report generated from those results:** After all parameterized tests complete, `@AfterAll generateEvidenceReport()` runs and calls `EvidenceReportGenerator.generate(reportDir, EVIDENCE_RESULTS)`. So the HTML and per-rule `results.json` files reflect the actual PASS/FAIL outcomes of the tests.

There is no separate test that only iterates and writes the report; the report is the output of the same tests that verify each rule.

## Format and style alignment (source of truth)

- **Layout and styling:** Copy the HTML structure, section order, CSS, and table design from the **sonar-gherkin-plugin** (or sonar-ansible-plugin) evidence report. When updating `EvidenceReportGenerator.java`, open the Gherkin plugin's evidence report generator and align:
  - Executive summary (summary box, counts, generated timestamp)
  - Methodology (numbered steps)
  - Rules index (table columns, styling, pass/fail colours)
  - Per-rule evidence (links to `rules/<ruleKey>/results.json`)
  - Attestation (statement and styling)
- **To match visual style:** The Terraform report generator has been aligned with the Gherkin plugin's <code>EvidenceReportGenerator.java</code>: same CSS variables (:root), header (navy bar, Q logo, meta), sticky nav, numbered sections (1–4), donut pass rate, KPI cards, source distribution bar, methodology cards, filter bar (search + source buttons), rules table styling, attestation box with sign-off fields, and footer. If the Gherkin report adds new sections or styles, sync them here.

## NFR references

- PRD §6.1 (NFR-2, NFR-3, NFR-4): same shape as Gherkin plugin; report from test runs; RuleEvidence/FixtureResult compatible with Gherkin/Ansible.
