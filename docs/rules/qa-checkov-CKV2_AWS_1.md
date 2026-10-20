# Ensure that all NACL are attached to subnets

`qa-checkov-CKV2_AWS_1` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that all NACL are attached to subnets.

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
