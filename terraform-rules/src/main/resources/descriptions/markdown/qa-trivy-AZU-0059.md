# Storage account should have secure transfer and minimum TLS version configured

`qa-trivy-AZU-0059` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Storage accounts should enforce HTTPS for secure data transfer and use a minimum TLS version of 1.2 or higher. This ensures that logs and data stored in the storage account are encrypted in transit. Azure Storage encrypts all data at rest by default, but transport encryption is also critical. Enable secure transfer and set minimum TLS version to TLS1_2 or higher.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0059](https://avd.aquasec.com/misconfig/azu-0059)
