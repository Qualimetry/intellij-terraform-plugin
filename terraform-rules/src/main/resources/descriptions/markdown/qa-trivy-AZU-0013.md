# Key Vault should have the network ACL block specified

`qa-trivy-AZU-0013` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Network ACLs allow you to reduce your exposure to risk by limiting what can access your key vault. The default action of the Network ACL should be set to deny for when IPs are not matched. Azure services can be allowed to bypass. Set a network ACL for the key vault.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0013](https://avd.aquasec.com/misconfig/azu-0013)
