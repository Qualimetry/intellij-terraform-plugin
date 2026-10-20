# Ensure AKS cluster has private cluster enabled

`qa-trivy-AZU-0065` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

A public AKS API server endpoint increases exposure to unauthorized access or attack. Enable private cluster to ensure the API server endpoint is only accessible from within the virtual network. Provision the AKS cluster with `private_cluster_enabled = true` and use private endpoints.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0065](https://avd.aquasec.com/misconfig/azu-0065)
