# Ensure linux VM enables SSH with keys for secure communication

`qa-checkov-CKV_AZURE_178` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure linux VM enables SSH with keys for secure communication.

## Noncompliant code example

```hcl
resource "azurerm_network_security_rule" "a" { name = "rdp"; priority = 100; direction = "Inbound"; access = "Allow"; protocol = "Tcp"; source_address_prefix = "*"; destination_port_range = "3389"; resource_group_name = "rg"; network_security_group_name = "nsg" }
```

## Compliant solution

```hcl
resource "azurerm_network_security_rule" "a" { name = "rdp"; priority = 100; direction = "Inbound"; access = "Deny"; protocol = "Tcp"; source_address_prefix = "*"; destination_port_range = "3389"; resource_group_name = "rg"; network_security_group_name = "nsg" }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
