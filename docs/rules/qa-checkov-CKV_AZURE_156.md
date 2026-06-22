# Ensure default Auditing policy for a SQL Server is configured to capture and retain the activity logs

`qa-checkov-CKV_AZURE_156` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure default Auditing policy for a SQL Server is configured to capture and retain the activity logs.

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
