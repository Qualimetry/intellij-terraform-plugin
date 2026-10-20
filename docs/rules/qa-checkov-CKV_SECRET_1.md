# Hard-coded secret: Artifactory Credentials

`qa-checkov-CKV_SECRET_1` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

The checkov secrets scanner found a literal in a Terraform file that matches the Artifactory Credentials format. Secrets committed in Terraform files are visible to everyone with repository access and are copied into plan and state output. Store the value in Azure Key Vault or AWS Secrets Manager and reference it from the configuration, or pass it in through a variable marked sensitive = true with no default.

## Noncompliant code example

```hcl
resource "azurerm_mssql_server" "db" {
  administrator_login          = "sqladmin"
  administrator_login_password = "kQ9zV2xL8mN4pR7sT1uW3yB6cD0fG5hJ"
}
```

## Compliant solution

```hcl
variable "db_password" {
  type      = string
  sensitive = true
}

resource "azurerm_mssql_server" "db" {
  administrator_login          = "sqladmin"
  administrator_login_password = var.db_password
}
```

## See also

- [https://www.checkov.io/5.Policy%20Index/secrets.html](https://www.checkov.io/5.Policy%20Index/secrets.html)
