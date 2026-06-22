# terraform.workspace with remote backend

`qa-tflint-terraform_workspace_remote` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

`terraform.workspace` should not be used with a remote backend when remote execution is enabled; Terraform Cloud uses the default workspace during remote runs. Use a variable instead.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { tags = { env = terraform.workspace } }
```

## Compliant solution

```hcl
variable "env" {}
resource "aws_instance" "a" { tags = { env = var.env } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_workspace_remote.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_workspace_remote.md)
