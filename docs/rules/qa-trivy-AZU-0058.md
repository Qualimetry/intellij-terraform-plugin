# Storage account should use geo-redundant replication

`qa-trivy-AZU-0058` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Geo-redundant storage (GRS) replicates your data to a secondary region that is hundreds of miles away from the primary region. This provides an additional level of durability for your data in the event of a complete regional outage or a disaster. Options include GRS, RAGRS, GZRS, and RAGZRS for geo-redundancy. Configure storage account to use geo-redundant replication (GRS, RAGRS, GZRS, or RAGZRS).

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0058](https://avd.aquasec.com/misconfig/azu-0058)
