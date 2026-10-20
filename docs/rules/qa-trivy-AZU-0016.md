# Key Vault should have purge protection enabled

`qa-trivy-AZU-0016` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Purge protection is an optional Key Vault behavior and is not enabled by default. Purge protection can only be enabled once soft-delete is enabled. It can be turned on via CLI or PowerShell. Enable purge protection for key vaults.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0016](https://avd.aquasec.com/misconfig/azu-0016)
