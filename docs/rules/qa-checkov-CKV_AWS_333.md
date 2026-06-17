# Ensure ECS services do not have public IP addresses assigned to them automatically

`qa-checkov-CKV_AWS_333` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure ECS services do not have public IP addresses assigned to them automatically.

## Noncompliant code example

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "public-read" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "private" }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
