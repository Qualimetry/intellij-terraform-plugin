Ensure that UDP Services are restricted from the Internet.

## Noncompliant Code Example

```hcl
resource "azurerm_network_security_rule" "a" { name = "rdp"; priority = 100; direction = "Inbound"; access = "Allow"; protocol = "Tcp"; source_address_prefix = "*"; destination_port_range = "3389"; resource_group_name = "rg"; network_security_group_name = "nsg" }
```

## Compliant Solution

```hcl
resource "azurerm_network_security_rule" "a" { name = "rdp"; priority = 100; direction = "Inbound"; access = "Deny"; protocol = "Tcp"; source_address_prefix = "*"; destination_port_range = "3389"; resource_group_name = "rg"; network_security_group_name = "nsg" }
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
