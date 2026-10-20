# Outputs should have description

`qa-tflint-terraform_documented_outputs` &middot; Maintainability &middot; Code Smell &middot; severity INFO

## Summary

Disallow `output` declarations without description. Adding a description helps documentation and tools like terraform-docs.

## Noncompliant code example

```hcl
output "name" { value = var.name }
```

## Compliant solution

```hcl
output "name" {
  description = "Display name"
  value     = var.name
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_outputs.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_outputs.md)
