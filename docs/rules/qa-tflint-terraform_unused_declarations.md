# Unused declarations

`qa-tflint-terraform_unused_declarations` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow variables, data sources, locals, and provider aliases that are declared but never used. Remove them or add an ignore annotation if intentional.

## Noncompliant code example

```hcl
variable "unused" {}
resource "aws_instance" "a" { ami = "ami-123" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-123" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_declarations.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_declarations.md)
