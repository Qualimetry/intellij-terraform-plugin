# Storage accounts should be configured to only accept transfers that are over secure connections

`qa-trivy-AZU-0008` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

You can configure your storage account to accept requests from secure connections only by setting the Secure transfer required property for the storage account. When you require secure transfer, any requests originating from an insecure connection are rejected. Microsoft recommends that you always require secure transfer for all of your storage accounts. Only allow secure connection for transferring data into storage accounts.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0008](https://avd.aquasec.com/misconfig/azu-0008)
