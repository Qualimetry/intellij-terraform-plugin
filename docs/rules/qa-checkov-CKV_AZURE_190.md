# Ensure that Storage blobs restrict public access

`qa-checkov-CKV_AZURE_190` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that Storage blobs restrict public access.

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
