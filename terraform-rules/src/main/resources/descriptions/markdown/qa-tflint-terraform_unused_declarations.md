Disallow variables, data sources, locals, and provider aliases that are declared but never used. Remove them or add an ignore annotation if intentional.

## Noncompliant Code Example

```hcl
variable "unused" {}
resource "aws_instance" "a" { ami = "ami-123" }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-123" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_declarations.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_declarations.md)
