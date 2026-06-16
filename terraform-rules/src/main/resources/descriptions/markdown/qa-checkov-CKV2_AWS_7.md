Ensure that Amazon EMR clusters' security groups are not open to the world.

## Noncompliant Code Example

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["0.0.0.0/0"]; security_group_id = "sg-123" }
```

## Compliant Solution

```hcl
resource "aws_security_group_rule" "a" { type = "ingress"; from_port = 22; to_port = 22; protocol = "tcp"; cidr_blocks = ["10.0.0.0/8"]; security_group_id = "sg-123"; description = "SSH from VPC" }
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
