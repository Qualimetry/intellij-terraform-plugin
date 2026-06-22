# Ensure Redis Cache is using the latest version of TLS encryption

`qa-checkov-CKV_AZURE_148` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure Redis Cache is using the latest version of TLS encryption.

## Noncompliant code example

```hcl
resource "azurerm_managed_disk" "a" { name = "disk"; location = "East US"; resource_group_name = "rg"; storage_account_type = "Standard_LRS" }
```

## Compliant solution

```hcl
resource "azurerm_managed_disk" "a" { name = "disk"; location = "East US"; resource_group_name = "rg"; storage_account_type = "Standard_LRS"; encryption_enabled = true }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
