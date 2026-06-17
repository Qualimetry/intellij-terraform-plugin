# Invalid AWS instance type

`qa-tflint-aws_instance_invalid_type` &middot; Reliability &middot; Bug &middot; severity MAJOR

## Summary

The instance_type attribute of aws_instance must be a valid AWS EC2 instance type. Invalid or deprecated types cause apply failures or runtime issues. See the AWS documentation for current instance types.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "invalid" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_invalid_type.md)
