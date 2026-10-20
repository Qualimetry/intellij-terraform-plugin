# The minimum TLS version for Storage Accounts should be TLS1_2 or higher

`qa-trivy-AZU-0011` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Azure Storage supports four versions of the TLS protocol: 1.0, 1.1, 1.2, and 1.3. Azure Storage uses TLS 1.2 or TLS 1.3 on public HTTPS endpoints, while TLS 1.0 and TLS 1.1 are still supported for backward compatibility. This check will warn if the minimum TLS version is set lower than TLS1_2. TLS1_2 and TLS1_3 are both allowed. Use a more recent TLS version for the storage account.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0011](https://avd.aquasec.com/misconfig/azu-0011)
