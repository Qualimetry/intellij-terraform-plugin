# Ensure the ELBv2 (Application/Network) has access logging enabled

`qa-checkov-CKV_AWS_91` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure the ELBv2 (Application/Network) has access logging enabled.

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
