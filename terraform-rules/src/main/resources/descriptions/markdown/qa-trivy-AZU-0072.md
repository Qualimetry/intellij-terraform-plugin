# Web App accepting traffic other than HTTPS

`qa-trivy-AZU-0072` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Allowing HTTP undermines transport encryption and exposes user data. Set 'HTTPS Only' to true in App Service TLS settings to force encrypted transport.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0072](https://avd.aquasec.com/misconfig/azu-0072)
