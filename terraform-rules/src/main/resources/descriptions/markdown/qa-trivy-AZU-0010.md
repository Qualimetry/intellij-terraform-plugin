# Trusted Microsoft Services should have bypass access to Storage accounts

`qa-trivy-AZU-0010` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Some Microsoft services that interact with storage accounts operate from networks that can't be granted access through network rules. To help this type of service work as intended, allow the set of trusted Microsoft services to bypass the network rules.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0010](https://avd.aquasec.com/misconfig/azu-0010)
