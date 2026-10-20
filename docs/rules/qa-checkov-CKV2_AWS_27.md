# Ensure Postgres RDS as aws_rds_cluster has Query Logging enabled

`qa-checkov-CKV2_AWS_27` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure Postgres RDS as aws_rds_cluster has Query Logging enabled.

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
