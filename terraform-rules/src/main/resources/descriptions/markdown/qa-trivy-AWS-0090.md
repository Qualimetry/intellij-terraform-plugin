# Enable S3 bucket versioning

`qa-trivy-AWS-0090` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

S3 data should be versioned. Enable versioning on the bucket (versioning block or aws_s3_bucket_versioning) to preserve, retrieve, and restore object versions and recover from unintended changes.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
resource "aws_s3_bucket_versioning" "a" { bucket = aws_s3_bucket.a.id; versioning_configuration { status = "Enabled" } }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0090](https://avd.aquasec.com/misconfig/aws-0090)
