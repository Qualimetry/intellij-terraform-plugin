# Deprecated interpolation

`qa-tflint-terraform_deprecated_interpolation` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow deprecated 0.11-style interpolation. Interpolation-only expressions like `"${var.x}"` are redundant in Terraform 0.12+; use `var.x` instead.

## Noncompliant code example

```hcl
resource "aws_instance" "a" {
  ami = "${var.ami}"
}
```

## Compliant solution

```hcl
resource "aws_instance" "a" {
  ami = var.ami
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_interpolation.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_interpolation.md)
