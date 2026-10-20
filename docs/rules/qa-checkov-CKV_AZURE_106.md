# Ensure that Azure Event Grid Domain public network access is disabled

`qa-checkov-CKV_AZURE_106` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that Azure Event Grid Domain public network access is disabled.

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
