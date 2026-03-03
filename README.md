# Qualimetry Terraform Analyzer - IntelliJ Plugin

Static analysis of Terraform (HCL) files (`.tf`) in IntelliJ IDEA and other JetBrains IDEs. Also runs in JetBrains Qodana for headless analysis in CI/CD pipelines.

Powered by tflint, Trivy, and Checkov with the Qualimetry rule registry. Same rule set as the [Qualimetry Terraform Analyzer for VS Code](https://marketplace.visualstudio.com/items?itemName=qualimetry.qualimetry-vscode-terraform-plugin) and the [Qualimetry Terraform Analyzer for SonarQube](https://github.com/Qualimetry/sonarqube-terraform-plugin).

## Features

- **400+ analysis rules** from tflint, Trivy, and Checkov covering AWS, Azure, GCP, and general Terraform best practices.
- **Real-time diagnostics** as you edit `.tf` files.
- **Configurable** — enable/disable individual rules and override severities via a per-rule settings panel.
- **SonarQube import** — import active rules from a SonarQube quality profile via Tools > Qualimetry Terraform > Import Rules from SonarQube.
- **Default quality profile** — curated set of rules active out of the box.
- **Qodana support** — runs automatically in JetBrains Qodana for quality gates in CI/CD.

## Prerequisites

The plugin runs tflint, Trivy, and Checkov as external tools. Install them on your PATH:

- [tflint](https://github.com/terraform-linters/tflint)
- [Trivy](https://aquasecurity.github.io/trivy/)
- [Checkov](https://www.checkov.io/)

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

After installation, configure the analyzer under **Settings > Tools > Qualimetry Terraform Analyzer**:

- **Enable/disable** the analyzer globally.
- **Per-rule table** — enable/disable individual rules, set severity overrides, filter by name or key.
- **Reset to Defaults** — clear all overrides and return to the default profile.
- Per-rule overrides are stored in `qualimetry-terraform.xml`.

### Import from SonarQube

Use **Tools > Qualimetry Terraform > Import Rules from SonarQube** to fetch active rules from a SonarQube quality profile. Enter your server URL, an optional authentication token, and an optional profile name. The imported rules replace the current configuration. The server URL and profile name are remembered between sessions.

## Requirements

- IntelliJ IDEA 2024.3 or later (any JetBrains IDE based on the IntelliJ Platform).
- tflint, Trivy, and/or Checkov on PATH.

## License

Apache License 2.0. See [LICENSE](LICENSE) for details.
