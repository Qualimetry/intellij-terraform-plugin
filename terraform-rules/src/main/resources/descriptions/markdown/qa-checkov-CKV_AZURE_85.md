# Ensure that Azure Defender is set to On for Kubernetes

`qa-checkov-CKV_AZURE_85` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that Azure Defender is set to On for Kubernetes.

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
