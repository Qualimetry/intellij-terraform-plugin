# Qualimetry Terraform Analyzer - IntelliJ Plugin

Static analysis of Terraform files (`.tf`) in IntelliJ IDEA and other JetBrains IDEs, including headless analysis in JetBrains Qodana for CI/CD pipelines.

Powered by the same analysis engine as the [VS Code extension](https://github.com/Qualimetry/vscode-terraform-plugin) and the [SonarQube plugin](https://github.com/Qualimetry/sonarqube-terraform-plugin).

## Features

- **766 rules** from tflint, Trivy, and checkov covering misconfigurations, security, style, and correctness.
- **Real-time diagnostics** as you edit `.tf` files.
- **Configurable** — enable/disable individual rules and override severities via a per-rule settings panel.
- **SonarQube import** — import active rules from a SonarQube quality profile via **Tools > Qualimetry Terraform > Import Rules from SonarQube**.
- **Qodana support** — runs automatically in JetBrains Qodana for quality gates in CI/CD.

## Rule categories

| Source | Focus | Examples |
|--------|-------|----------|
| tflint | Terraform conventions | Naming conventions, deprecated syntax, provider requirements |
| Trivy | Security misconfigurations | S3 encryption, IAM policies, network security groups |
| checkov | Compliance & best practices | CIS benchmarks, SOC2, HIPAA, PCI-DSS checks |

## Prerequisites

The plugin runs tflint, Trivy, and checkov as external tools. Install them on your PATH:

- [tflint](https://github.com/terraform-linters/tflint)
- [Trivy](https://aquasecurity.github.io/trivy/)
- [checkov](https://www.checkov.io/)

If a tool is not installed, findings from that tool are silently skipped.

## Installation

### From JetBrains Marketplace

1. Open **Settings > Plugins > Marketplace**.
2. Search for **Qualimetry Terraform Analyzer**.
3. Click **Install** and restart.

### From source

```bash
cd <monorepo-root>
mvn clean install -pl terraform-rules

cd intellij-plugin
./gradlew buildPlugin
```

The plugin ZIP is produced in `build/distributions/`.

## Configuration

Configure the analyzer under **Settings > Tools > Qualimetry Terraform Analyzer**:

- **Enable/disable** the analyzer globally.
- **Per-rule table** — enable/disable individual rules, set severity overrides, filter by name or key.
- **Reset to Defaults** — clear all overrides and return to the default profile.
- Per-rule overrides are stored in `qualimetry-terraform.xml`.

### Import from SonarQube

Use **Tools > Qualimetry Terraform > Import Rules from SonarQube** to fetch active rules from a SonarQube quality profile. Enter your server URL, an optional authentication token, and an optional profile name. The imported rules replace the current configuration. The server URL and profile name are remembered between sessions.

## Also available

The same analysis engine powers plugins for other platforms:

- **[VS Code extension](https://github.com/Qualimetry/vscode-terraform-plugin)** — catch issues as you type in VS Code.
- **[SonarQube plugin](https://github.com/Qualimetry/sonarqube-terraform-plugin)** — enforce quality gates in CI/CD pipelines.

Rule keys and severities align across all three tools so findings are directly comparable.

## Requirements

- IntelliJ IDEA 2024.3 or later (any JetBrains IDE based on the IntelliJ Platform).
- tflint, Trivy, and/or checkov on PATH.

## License

Apache License 2.0. See [LICENSE](LICENSE) for details.
