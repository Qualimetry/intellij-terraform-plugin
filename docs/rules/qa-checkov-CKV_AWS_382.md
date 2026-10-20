# Ensure no security groups allow egress from 0.0.0.0:0 to port -1

`qa-checkov-CKV_AWS_382` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure no security groups allow egress from 0.0.0.0:0 to port -1.

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
