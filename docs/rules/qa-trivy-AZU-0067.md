# Ensure AKS cluster has disk encryption set ID configured

`qa-trivy-AZU-0067` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Azure Kubernetes clusters should define a disk encryption set ID to ensure encrypted storage for OS and data disks. This provides an additional layer of security by encrypting data at rest. Configure a disk encryption set ID for the AKS cluster to enable customer-managed key encryption.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0067](https://avd.aquasec.com/misconfig/azu-0067)
