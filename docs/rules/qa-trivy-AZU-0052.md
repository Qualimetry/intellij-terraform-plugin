# Role definition allows custom role creation

`qa-trivy-AZU-0052` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Allowing custom roles to include 'roleDefinitions/write' enables privilege escalation. A user could define or alter roles to gain excessive permissions. Avoid granting 'Microsoft.Authorization/roleDefinitions/write' permission in custom roles. Restrict role creation capability to core admins only.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0052](https://avd.aquasec.com/misconfig/azu-0052)
