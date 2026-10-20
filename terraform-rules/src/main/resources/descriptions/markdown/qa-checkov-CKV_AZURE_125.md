# Ensures that Service Fabric use three levels of protection available

`qa-checkov-CKV_AZURE_125` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensures that Service Fabric use three levels of protection available.

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
