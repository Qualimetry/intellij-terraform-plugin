# Ensure that only encrypted EBS volumes are attached to EC2 instances

`qa-checkov-CKV2_AWS_2` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that only encrypted EBS volumes are attached to EC2 instances.

## Noncompliant code example

```hcl
resource "aws_ebs_volume" "a" { size = 10; availability_zone = "us-east-1a" }
```

## Compliant solution

```hcl
resource "aws_ebs_volume" "a" { size = 10; availability_zone = "us-east-1a"; encrypted = true }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
