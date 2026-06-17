# Variables should have description

`qa-tflint-terraform_documented_variables` &middot; Maintainability &middot; Code Smell &middot; severity INFO

## Summary

Disallow `variable` declarations without description. Descriptions clarify purpose and are useful when combined with terraform-docs.

## Noncompliant code example

```hcl
variable "name" {}
```

## Compliant solution

```hcl
variable "name" {
  description = "Resource name"
  type        = string
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_variables.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_variables.md)
