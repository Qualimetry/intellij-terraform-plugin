# Ensure that the expiration date is set on all secrets

`qa-checkov-CKV_AZURE_41` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that the expiration date is set on all secrets.

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
