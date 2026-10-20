# Storage account should use customer-managed keys for encryption

`qa-trivy-AZU-0060` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Storage accounts should use customer-managed keys (CMK) for encryption to provide additional control over the encryption keys. Customer-managed keys allow you to create, rotate, disable, and revoke access controls. They also provide greater flexibility to audit the encryption keys that are used to protect your data. Configure customer-managed keys for storage account encryption.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0060](https://avd.aquasec.com/misconfig/azu-0060)
