Require specific tags for all AWS resource types that support them. Standardized tags help with cost and operations.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro"; tags = { Name = "my-instance", Env = "prod" } }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_resource_missing_tags.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_resource_missing_tags.md)
