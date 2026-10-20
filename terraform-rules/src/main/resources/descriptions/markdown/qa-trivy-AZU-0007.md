# Storage containers in blob storage mode should not have public access

`qa-trivy-AZU-0007` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Storage container public access should be off. It can be configured for blobs only, containers and blobs or off entirely. The default is off, with no public access. Explicitly overriding publicAccess to anything other than off should be avoided. Disable public access to storage containers.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0007](https://avd.aquasec.com/misconfig/azu-0007)
