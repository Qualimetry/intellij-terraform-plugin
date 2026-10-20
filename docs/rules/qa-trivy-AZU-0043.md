# Ensure AKS cluster has Network Policy configured

`qa-trivy-AZU-0043` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

The Kubernetes object type NetworkPolicy should be defined to have the opportunity to allow or block traffic to pods, as in a Kubernetes cluster configured with default settings, all pods can discover and communicate with each other without any restrictions. Configure a network policy.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0043](https://avd.aquasec.com/misconfig/azu-0043)
