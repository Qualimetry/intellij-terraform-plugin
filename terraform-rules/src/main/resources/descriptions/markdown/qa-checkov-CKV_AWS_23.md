# Ensure every security group and rule has a description

`qa-checkov-CKV_AWS_23` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Ensure every security group and rule has a description.

## Noncompliant code example

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"]; security_group_id = "sg-123" }
```

## Compliant solution

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["10.0.0.0/8"]; security_group_id = "sg-123"; description = "SSH from VPC" }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
