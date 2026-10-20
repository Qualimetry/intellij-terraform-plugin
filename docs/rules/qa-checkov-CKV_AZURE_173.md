# Ensure API management uses at least TLS 1.2

`qa-checkov-CKV_AZURE_173` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure API management uses at least TLS 1.2.

## Noncompliant code example

```hcl
resource "azurerm_storage_account" "a" { name = "sa"; resource_group_name = "rg"; location = "East US" }
```

## Compliant solution

```hcl
resource "azurerm_storage_account" "a" { name = "sa"; resource_group_name = "rg"; location = "East US"; enable_https_traffic_only = true }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
