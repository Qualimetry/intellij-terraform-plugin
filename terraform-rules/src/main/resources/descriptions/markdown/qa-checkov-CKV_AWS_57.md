# S3 Bucket has an ACL defined which allows public WRITE access.

`qa-checkov-CKV_AWS_57` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

S3 Bucket has an ACL defined which allows public WRITE access..

## Noncompliant code example

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "public-read-write" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "private" }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
