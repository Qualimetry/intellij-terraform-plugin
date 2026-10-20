# Security group inline rules

`qa-tflint-aws_security_group_inline_rules` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Prefer separate aws_security_group_rule resources over inline ingress/egress blocks for better management.

## Noncompliant code example

```hcl
resource "aws_security_group" "a" { name = "x"; ingress { from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"] } }
```

## Compliant solution

```hcl
resource "aws_security_group" "a" { name = "x" }
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"]; security_group_id = aws_security_group.a.id }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_inline_rules.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_inline_rules.md)
