# Previous generation EC2 instance type

`qa-tflint-aws_instance_previous_type` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow previous-generation EC2 instance types (e.g. t1, m1); use current generation for support and performance.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t1.micro" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_previous_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_previous_type.md)
