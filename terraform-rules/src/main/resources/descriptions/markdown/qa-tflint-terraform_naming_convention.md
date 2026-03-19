Enforces a naming convention for Terraform identifiers (resources, variables, outputs, locals, modules, data sources). Default format is snake_case; can be configured per block type. Non-compliant names (e.g. camelCase) are reported so that style stays consistent and readable.

## Noncompliant Code Example

```hcl
resource "aws_instance" "myInstance" {}
```

## Compliant Solution

```hcl
resource "aws_instance" "my_instance" {}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_naming_convention.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_naming_convention.md)
