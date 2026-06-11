Ensure 'Enforce SSL connection' is set to 'ENABLED' for MySQL Database Server.

## Noncompliant Code Example

```hcl
resource "azurerm_resource_group" "a" { name = "rg"; location = "East US" }
```

## Compliant Solution

```hcl
resource "azurerm_resource_group" "a" { name = "rg"; location = "East US" }
# Add the required Azure configuration per Checkov policy.
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
