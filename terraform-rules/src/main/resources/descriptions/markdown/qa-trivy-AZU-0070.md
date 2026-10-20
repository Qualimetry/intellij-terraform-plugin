# App Service using unsupported Python version

`qa-trivy-AZU-0070` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Using an unsupported Python runtime in Azure App Service may expose applications to security vulnerabilities as these versions no longer receive security patches. This check ensures Python versions are still supported by the Python Foundation. Update to a supported Python version (3.9 or higher). Consider migrating from azurerm_app_service to azurerm_linux_web_app for access to modern Python versions.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0070](https://avd.aquasec.com/misconfig/azu-0070)
