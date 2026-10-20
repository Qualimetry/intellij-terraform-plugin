# Ensure AWS RDS database instance configured with encryption in transit

`qa-checkov-CKV2_AWS_69` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure AWS RDS database instance configured with encryption in transit.

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
