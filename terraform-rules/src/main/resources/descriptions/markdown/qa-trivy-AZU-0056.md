# Storage account should have blob soft delete enabled

`qa-trivy-AZU-0056` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Blob soft delete protects blob data from being accidentally or erroneously modified or deleted. When blob soft delete is enabled, deleted blobs are retained in the system for a specified retention period. During the retention period, you can restore a soft-deleted blob to its state at the time it was deleted. Enable soft delete for blobs with an appropriate retention period.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0056](https://avd.aquasec.com/misconfig/azu-0056)
