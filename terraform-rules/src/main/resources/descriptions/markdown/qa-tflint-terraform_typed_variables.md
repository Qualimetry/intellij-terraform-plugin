Disallow `variable` declarations without type. Explicit types improve clarity and catch errors early.

## Noncompliant Code Example

```hcl
variable "name" { default = "x" }
```

## Compliant Solution

```hcl
variable "name" { type = string; default = "x" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_typed_variables.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_typed_variables.md)
