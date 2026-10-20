# Ensure that Azure Cognitive Search maintains SLA for search index queries

`qa-checkov-CKV_AZURE_209` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that Azure Cognitive Search maintains SLA for search index queries.

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
