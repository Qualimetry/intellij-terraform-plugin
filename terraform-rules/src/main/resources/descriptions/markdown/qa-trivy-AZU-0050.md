# Security group should not allow unrestricted ingress to SSH port from any IP address

`qa-trivy-AZU-0050` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

SSH access can be configured on either the network security group or in the network security group rule. SSH access should not be permitted from the internet (*, 0.0.0.0, /0, internet, any). Block port 22 access from the internet.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0050](https://avd.aquasec.com/misconfig/azu-0050)
