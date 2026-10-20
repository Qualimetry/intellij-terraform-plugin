# A security rule should not allow unrestricted egress to any IP address

`qa-trivy-AZU-0051` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Opening up ports to connect out to the public internet is generally to be avoided. You should restrict access to IP addresses or ranges that are explicitly required where possible. Set a more restrictive cidr range.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0051](https://avd.aquasec.com/misconfig/azu-0051)
