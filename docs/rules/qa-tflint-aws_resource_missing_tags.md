# AWS resource missing tags

`qa-tflint-aws_resource_missing_tags` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Require specific tags for all AWS resource types that support them. Standardized tags help with cost and operations.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro"; tags = { Name = "my-instance", Env = "prod" } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_resource_missing_tags.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_resource_missing_tags.md)
