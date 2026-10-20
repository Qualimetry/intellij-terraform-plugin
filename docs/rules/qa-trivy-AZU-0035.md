# Data Factory should have public access disabled, the default is enabled

`qa-trivy-AZU-0035` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Data Factory has public access set to true by default. Disabling public network access is applicable only to the self-hosted integration runtime, not to Azure Integration Runtime and SQL Server Integration Services (SSIS) Integration Runtime. Set public access to disabled for Data Factory.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0035](https://avd.aquasec.com/misconfig/azu-0035)
