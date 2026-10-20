# Terraform naming convention

`qa-tflint-terraform_naming_convention` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Enforces a naming convention for Terraform identifiers (resources, variables, outputs, locals, modules, data sources). Default format is snake_case; can be configured per block type. Non-compliant names (e.g. camelCase) are reported so that style stays consistent and readable.

## Noncompliant code example

```hcl
resource "aws_instance" "myInstance" {}
```

## Compliant solution

```hcl
resource "aws_instance" "my_instance" {}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_naming_convention.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_naming_convention.md)
