# App Service using unsupported PHP version

`qa-trivy-AZU-0069` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Using an unsupported PHP runtime in Azure App Service may expose applications to security vulnerabilities as these versions no longer receive security patches. This check ensures PHP versions are still supported. Update to a supported PHP version (8.1 or higher). Consider migrating from azurerm_app_service to azurerm_linux_web_app for access to modern PHP versions.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0069](https://avd.aquasec.com/misconfig/azu-0069)
