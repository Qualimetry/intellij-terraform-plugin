# Deprecated dot index syntax

`qa-tflint-terraform_deprecated_index` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow legacy dot index syntax for list access. Use square brackets (e.g. `list[0]`, `list[*].a`) instead of `list.0` or `list.*.a`; dot syntax is no longer documented.

## Noncompliant code example

```hcl
output "x" { value = var.list.0 }
output "y" { value = var.items.*.id }
```

## Compliant solution

```hcl
output "x" { value = var.list[0] }
output "y" { value = [for i in var.items : i.id] }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_index.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_index.md)
