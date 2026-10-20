# Ensure all data stored in the SQS queue is encrypted

`qa-checkov-CKV_AWS_27` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure all data stored in the SQS queue is encrypted.

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
