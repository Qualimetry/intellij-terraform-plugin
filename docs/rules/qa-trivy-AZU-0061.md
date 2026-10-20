# Storage account should have infrastructure encryption enabled

`qa-trivy-AZU-0061` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Infrastructure encryption provides an additional layer of encryption at the infrastructure level. When infrastructure encryption is enabled, data in the storage account is encrypted twice - once at the service level and once at the infrastructure level with two different encryption algorithms. This provides double encryption for enhanced security. Enable infrastructure encryption for storage account.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0061](https://avd.aquasec.com/misconfig/azu-0061)
