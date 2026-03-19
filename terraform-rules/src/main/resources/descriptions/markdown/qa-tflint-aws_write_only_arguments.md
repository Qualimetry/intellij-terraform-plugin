Disallow arguments that are write-only (not readable after apply); can cause drift and state issues.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro"; password = "secret" }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_write_only_arguments.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_write_only_arguments.md)
