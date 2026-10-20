# Avoid the use of local users for Azure Storage unless necessary

`qa-checkov-CKV_AZURE_244` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Avoid the use of local users for Azure Storage unless necessary.

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
