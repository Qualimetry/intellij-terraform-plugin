Disallow `output` declarations without description. Adding a description helps documentation and tools like terraform-docs.

## Noncompliant Code Example

```hcl
output "name" { value = var.name }
```

## Compliant Solution

```hcl
output "name" {
  description = "Display name"
  value     = var.name
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_outputs.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_outputs.md)
