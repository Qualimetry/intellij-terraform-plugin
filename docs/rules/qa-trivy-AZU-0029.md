# Ensure database firewalls do not permit public access

`qa-trivy-AZU-0029` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Azure services can be allowed access through the firewall using a start and end IP address of 0.0.0.0. No other end ip address should be combined with a start of 0.0.0.0. Do not use wide IP ranges for the SQL firewall.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0029](https://avd.aquasec.com/misconfig/azu-0029)
