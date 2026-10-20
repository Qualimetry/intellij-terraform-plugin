# The default action on Storage account network rules should be set to deny

`qa-trivy-AZU-0012` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

The default_action for network rules should come into effect when no other rules are matched. The default action should be set to Deny. Set network rules to deny.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0012](https://avd.aquasec.com/misconfig/azu-0012)
