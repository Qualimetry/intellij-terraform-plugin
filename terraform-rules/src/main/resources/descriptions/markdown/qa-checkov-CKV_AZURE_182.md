# Ensure that VNET has at least 2 connected DNS Endpoints

`qa-checkov-CKV_AZURE_182` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that VNET has at least 2 connected DNS Endpoints.

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
