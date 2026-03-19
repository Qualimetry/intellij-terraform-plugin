Disallow deprecated attribute usage in aws_security_group_rule.

## Noncompliant Code Example

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; security_group_id = "sg-123"; cidr_blocks = ["0.0.0.0/0"] }
```

## Compliant Solution

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"]; security_group_id = "sg-123" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_rule_deprecated.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_rule_deprecated.md)
