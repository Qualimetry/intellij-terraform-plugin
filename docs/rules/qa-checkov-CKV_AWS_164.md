# Ensure Transfer Server is not exposed publicly.

`qa-checkov-CKV_AWS_164` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure Transfer Server is not exposed publicly..

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
