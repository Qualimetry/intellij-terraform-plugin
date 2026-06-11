The instance_type attribute of aws_instance must be a valid AWS EC2 instance type. Invalid or deprecated types cause apply failures or runtime issues. See the AWS documentation for current instance types.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "invalid" }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_invalid_type.md)
