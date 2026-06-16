Security group rules must use valid protocol names or numbers (tcp, udp, icmp, etc.).

## Noncompliant Code Example

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; protocol = "invalid"; security_group_id = "sg-123" }
```

## Compliant Solution

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; security_group_id = "sg-123" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_invalid_protocol.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_security_group_invalid_protocol.md)
