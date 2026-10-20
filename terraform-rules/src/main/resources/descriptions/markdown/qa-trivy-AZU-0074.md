# Sensitive port is exposed to entire network

`qa-trivy-AZU-0074` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Sensitive legacy ports like Telnet or POP3 should not be open to broad networks. Remove NSG rules allowing legacy or unencrypted protocols on broad scopes.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0074](https://avd.aquasec.com/misconfig/azu-0074)
