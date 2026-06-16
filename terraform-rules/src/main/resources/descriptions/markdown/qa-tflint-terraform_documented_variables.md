Disallow `variable` declarations without description. Descriptions clarify purpose and are useful when combined with terraform-docs.

## Noncompliant Code Example

```hcl
variable "name" {}
```

## Compliant Solution

```hcl
variable "name" {
  description = "Resource name"
  type        = string
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_variables.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_documented_variables.md)
