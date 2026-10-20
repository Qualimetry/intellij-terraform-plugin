# Ensure DLM cross region schedules are encrypted using a Customer Managed Key

`qa-checkov-CKV_AWS_256` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure DLM cross region schedules are encrypted using a Customer Managed Key.

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
