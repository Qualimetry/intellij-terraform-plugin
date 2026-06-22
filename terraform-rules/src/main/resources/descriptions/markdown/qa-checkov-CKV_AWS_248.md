# Ensure that Elasticsearch is not using the default Security Group

`qa-checkov-CKV_AWS_248` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that Elasticsearch is not using the default Security Group.

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
