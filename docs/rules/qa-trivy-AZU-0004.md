# Ensure the Function App can only be accessed via HTTPS

`qa-trivy-AZU-0004` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

By default, clients can connect to function endpoints by using both HTTP or HTTPS. You should redirect HTTP to HTTPS because HTTPS uses the SSL/TLS protocol to provide a secure connection, which is both encrypted and authenticated. You can redirect all HTTP requests to the HTTPS port.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0004](https://avd.aquasec.com/misconfig/azu-0004)
