Prefer separate aws_security_group_rule resources over inline ingress/egress blocks for better management.

## Noncompliant Code Example

```hcl
resource "aws_security_group" "a" { name = "x"; ingress { from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"] } }
```

## Compliant Solution

```hcl
resource "aws_security_group" "a" { name = "x" }
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"]; security_group_id = aws_security_group.a.id }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_inline_rules.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_inline_rules.md)
