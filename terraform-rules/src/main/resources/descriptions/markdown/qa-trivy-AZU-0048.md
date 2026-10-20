# A security group should not allow unrestricted ingress to the RDP port from any IP address

`qa-trivy-AZU-0048` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

RDP access can be configured on either the network security group or in the network security group rule. RDP access should not be permitted from the internet (*, 0.0.0.0, /0, internet, any). Consider using the Azure Bastion Service. Block RDP port from internet.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0048](https://avd.aquasec.com/misconfig/azu-0048)
