# Ensure App configuration encryption block is set.

`qa-checkov-CKV_AZURE_186` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure App configuration encryption block is set..

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
