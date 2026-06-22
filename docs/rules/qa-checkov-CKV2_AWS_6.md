# Ensure that S3 bucket has a Public Access block

`qa-checkov-CKV2_AWS_6` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure that S3 bucket has a Public Access block.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true; block_public_policy = true; ignore_public_acls = true; restrict_public_buckets = true }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
