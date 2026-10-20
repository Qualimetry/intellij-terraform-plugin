# Retention policy for flow logs should be enabled and set to greater than 90 days

`qa-trivy-AZU-0049` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Flow logs are the source of truth for all network activity in your cloud environment. To enable analysis in security event that was detected late, you need to have the logs available. Setting a retention policy will help ensure as much information is available for review. Ensure flow log retention is turned on with an expiry of >90 days.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0049](https://avd.aquasec.com/misconfig/azu-0049)
