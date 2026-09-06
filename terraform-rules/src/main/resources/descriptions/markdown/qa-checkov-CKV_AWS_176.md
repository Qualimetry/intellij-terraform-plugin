# Ensure Logging is enabled for WAF Web Access Control Lists

`qa-checkov-CKV_AWS_176` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure Logging is enabled for WAF Web Access Control Lists.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket"; logging { target_bucket = aws_s3_bucket.logs.id; target_prefix = "log/" } }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
