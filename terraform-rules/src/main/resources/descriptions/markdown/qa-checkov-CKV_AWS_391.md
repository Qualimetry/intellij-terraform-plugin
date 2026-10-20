# Avoid AWS Redshift cluster with commonly used master username and public access setting enabled

`qa-checkov-CKV_AWS_391` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Avoid AWS Redshift cluster with commonly used master username and public access setting enabled.

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
