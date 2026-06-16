Disallow deprecated 0.11-style interpolation. Interpolation-only expressions like `"${var.x}"` are redundant in Terraform 0.12+; use `var.x` instead.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" {
  ami = "${var.ami}"
}
```

## Compliant Solution

```hcl
resource "aws_instance" "a" {
  ami = var.ami
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_interpolation.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_interpolation.md)
