# Key Vault secret should have a content type set

`qa-trivy-AZU-0015` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Content Type is an optional Key Vault Secret behavior and is not enabled by default. Clients may specify the content type of a secret to assist in interpreting the secret data when it's retrieved. The maximum length of this field is 255 characters. There are no pre-defined values. The suggested usage is as a hint for interpreting the secret data. Provide content type for secrets to aid interpretation on retrieval.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0015](https://avd.aquasec.com/misconfig/azu-0015)
