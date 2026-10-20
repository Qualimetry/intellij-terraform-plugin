# Ensure that Network Interfaces don't use public IPs

`qa-checkov-CKV_AZURE_119` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that Network Interfaces don't use public IPs.

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
