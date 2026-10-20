# Write-only arguments

`qa-tflint-aws_write_only_arguments` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Disallow arguments that are write-only (not readable after apply); can cause drift and state issues.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro"; password = "secret" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_write_only_arguments.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_write_only_arguments.md)
