# Ensure that Register with Azure Active Directory is enabled on App Service

`qa-checkov-CKV_AZURE_16` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that Register with Azure Active Directory is enabled on App Service.

## Noncompliant code example

```hcl
resource "azurerm_resource_group" "a" { name = "rg"; location = "East US" }
```

## Compliant solution

```hcl
resource "azurerm_resource_group" "a" { name = "rg"; location = "East US" }
# Add the required Azure configuration per Checkov policy.
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
