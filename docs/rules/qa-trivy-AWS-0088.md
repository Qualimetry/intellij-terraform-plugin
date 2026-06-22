# S3 buckets should have encryption enabled

`qa-trivy-AWS-0088` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

S3 buckets should have server-side encryption enabled. Use aws_s3_bucket_server_side_encryption_configuration to enable SSE-S3 or SSE-KMS.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
resource "aws_s3_bucket_server_side_encryption_configuration" "a" { bucket = aws_s3_bucket.a.id; rule { apply_server_side_encryption_by_default { sse_algorithm = "AES256" } } }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0088](https://avd.aquasec.com/misconfig/aws-0088)
