# Ensure VPC subnets do not assign public IP by default

`qa-checkov-CKV_AWS_130` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure VPC subnets do not assign public IP by default.

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
