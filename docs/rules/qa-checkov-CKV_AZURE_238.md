# Ensure that all Azure Cognitive Services accounts are configured with a managed identity

`qa-checkov-CKV_AZURE_238` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that all Azure Cognitive Services accounts are configured with a managed identity.

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
