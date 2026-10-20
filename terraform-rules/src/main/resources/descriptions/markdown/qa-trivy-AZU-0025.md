# Database auditing retention period should be longer than 90 days

`qa-trivy-AZU-0025` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

When Auditing is configured for a SQL database, if the retention period is not set, the retention will be unlimited. If the retention period is to be explicitly set, it should be set for no less than 90 days. Set retention periods of database auditing to greater than 90 days.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0025](https://avd.aquasec.com/misconfig/azu-0025)
