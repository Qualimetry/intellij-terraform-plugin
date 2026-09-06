# Ensure SNS topic policy is not public by only allowing specific services or principals to access it

`qa-checkov-CKV_AWS_169` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure SNS topic policy is not public by only allowing specific services or principals to access it.

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
