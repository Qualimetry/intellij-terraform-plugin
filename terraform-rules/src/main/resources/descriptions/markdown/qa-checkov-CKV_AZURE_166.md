# Ensure container image quarantine, scan, and mark images verified

`qa-checkov-CKV_AZURE_166` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure container image quarantine, scan, and mark images verified.

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
