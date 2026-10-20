# Ensure server parameter 'log_checkpoints' is set to 'ON' for PostgreSQL Database Server

`qa-checkov-CKV_AZURE_30` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure server parameter 'log_checkpoints' is set to 'ON' for PostgreSQL Database Server.

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
