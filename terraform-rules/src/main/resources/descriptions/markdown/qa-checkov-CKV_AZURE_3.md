Ensure that 'enable_https_traffic_only' is enabled.

## Noncompliant Code Example

```hcl
resource "azurerm_storage_account" "a" { name = "sa"; resource_group_name = "rg"; location = "East US" }
```

## Compliant Solution

```hcl
resource "azurerm_storage_account" "a" { name = "sa"; resource_group_name = "rg"; location = "East US"; enable_https_traffic_only = true }
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
