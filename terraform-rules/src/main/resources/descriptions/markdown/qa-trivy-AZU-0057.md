# Storage account should have logging enabled

`qa-trivy-AZU-0057` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Storage Analytics logs detailed information about successful and failed requests to a storage service. This information can be used to monitor individual requests and to diagnose issues with a storage service. Logging should be enabled for at least one storage service type. Enable logging for at least one storage service (Queue, Table, or Blob).

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0057](https://avd.aquasec.com/misconfig/azu-0057)
