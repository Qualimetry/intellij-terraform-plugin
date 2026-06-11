# Changelog

All notable changes to the Qualimetry Terraform Analyzer for IntelliJ are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.2.6] - 2026-06-12

### Fixed

- SonarQube rule import now applies rule parameters and severities, and reuses the saved connection token.

## [2.2.5] - 2026-06-11

### Added

- Rules now re-sync automatically from the last-used SonarQube server and profile on IDE startup (toggle in settings).
- **Import from SonarQube** button on the settings page.
- SonarQube tokens are stored securely in the IDE credential store.
- Notification when the Terraform tool cannot be found.

## [2.2.3] - 2026-03-04

### Added

- Initial release of the Terraform Analyzer plugin for IntelliJ IDEA and other JetBrains IDEs.
- **400+ analysis rules** from tflint, Trivy, and Checkov (AWS, Azure, GCP, general).
- Runs tflint, Trivy, and Checkov as external tools and maps findings to the Qualimetry rule registry.
- Per-rule settings panel with enable/disable, severity override, and search filter under Settings > Tools.
- **Import from SonarQube** — fetch active rules from a SonarQube quality profile via Tools > Qualimetry Terraform > Import Rules from SonarQube.
- Per-rule inspection options for Qodana profile configuration.
- Compatible with JetBrains Qodana for headless CI/CD analysis.
- Same rule registry as the Qualimetry Terraform Analyzer for VS Code and SonarQube.

## [2.2.2] - 2026-03-03

### Added

- Initial release of the Terraform Analyzer plugin for IntelliJ IDEA and other JetBrains IDEs.
- **400+ analysis rules** from tflint, Trivy, and Checkov (AWS, Azure, GCP, general).
- Runs tflint, Trivy, and Checkov as external tools and maps findings to the Qualimetry rule registry.
- Per-rule settings panel with enable/disable, severity override, and search filter under Settings > Tools.
- **Import from SonarQube** — fetch active rules from a SonarQube quality profile via Tools > Qualimetry Terraform > Import Rules from SonarQube.
- Per-rule inspection options for Qodana profile configuration.
- Compatible with JetBrains Qodana for headless CI/CD analysis.
- Same rule registry as the Qualimetry Terraform Analyzer for VS Code and SonarQube.
