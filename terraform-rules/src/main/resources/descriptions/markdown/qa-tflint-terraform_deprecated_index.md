Disallow legacy dot index syntax for list access. Use square brackets (e.g. `list[0]`, `list[*].a`) instead of `list.0` or `list.*.a`; dot syntax is no longer documented.

## Noncompliant Code Example

```hcl
output "x" { value = var.list.0 }
output "y" { value = var.items.*.id }
```

## Compliant Solution

```hcl
output "x" { value = var.list[0] }
output "y" { value = [for i in var.items : i.id] }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_index.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_index.md)
