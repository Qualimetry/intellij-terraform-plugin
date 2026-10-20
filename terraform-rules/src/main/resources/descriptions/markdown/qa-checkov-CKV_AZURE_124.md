# Ensure that Azure Cognitive Search disables public network access

`qa-checkov-CKV_AZURE_124` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that Azure Cognitive Search disables public network access.

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
