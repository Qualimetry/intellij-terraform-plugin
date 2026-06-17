# Enable at-rest encryption for EC2

`qa-trivy-AWS-0131` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Instance block devices should be encrypted. Set root_block_device and ebs_block_device encrypted = true on aws_instance so sensitive data is held securely at rest.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro"; root_block_device { encrypted = true }; ebs_block_device { device_name = "/dev/sdb"; encrypted = true } }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0131](https://avd.aquasec.com/misconfig/aws-0131)
