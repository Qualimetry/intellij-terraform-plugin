Disallow previous-generation EC2 instance types (e.g. t1, m1); use current generation for support and performance.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t1.micro" }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_previous_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_instance_previous_type.md)
