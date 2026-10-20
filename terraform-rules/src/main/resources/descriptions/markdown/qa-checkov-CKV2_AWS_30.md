# Ensure Postgres RDS as aws_db_instance has Query Logging enabled

`qa-checkov-CKV2_AWS_30` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure Postgres RDS as aws_db_instance has Query Logging enabled.

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
