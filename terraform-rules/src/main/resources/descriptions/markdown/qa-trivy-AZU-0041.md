# Ensure AKS has API server authorized IP ranges enabled

`qa-trivy-AZU-0041` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

The API server is the central way to interact with and manage a cluster. To improve cluster security and minimize attacks, the API server should only be accessible from a limited set of IP address ranges. Limit the access to the API server to a limited IP range.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0041](https://avd.aquasec.com/misconfig/azu-0041)
