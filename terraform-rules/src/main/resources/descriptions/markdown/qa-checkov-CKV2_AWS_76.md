# Ensure AWS ALB attached WAFv2 WebACL is configured with AMR for Log4j Vulnerability

`qa-checkov-CKV2_AWS_76` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure AWS ALB attached WAFv2 WebACL is configured with AMR for Log4j Vulnerability.

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
