# App Service FTPS enforce disabled

`qa-trivy-AZU-0071` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Allowing plain FTP risks credentials and data being transmitted unencrypted. Set FTPS state to 'FTPS Only' in App Service settings to prevent plaintext FTP.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0071](https://avd.aquasec.com/misconfig/azu-0071)
