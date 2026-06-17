# Variables should have type

`qa-tflint-terraform_typed_variables` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow `variable` declarations without type. Explicit types improve clarity and catch errors early.

## Noncompliant code example

```hcl
variable "name" { default = "x" }
```

## Compliant solution

```hcl
variable "name" { type = string; default = "x" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_typed_variables.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_typed_variables.md)
