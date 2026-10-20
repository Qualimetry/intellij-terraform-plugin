# Databases should have the minimum TLS set for connections

`qa-trivy-AZU-0026` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

You should not use outdated/insecure TLS versions for encryption. You should be using TLS v1.2+. Use the most modern TLS policies available.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0026](https://avd.aquasec.com/misconfig/azu-0026)
