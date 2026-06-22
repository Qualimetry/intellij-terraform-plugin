# Restrict public buckets

`qa-trivy-AWS-0093` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

S3 Access block should restrict public bucket to limit access. Set restrict_public_buckets = true on aws_s3_bucket_public_access_block so that only the bucket owner and AWS services can access when a public policy exists.

## Noncompliant code example

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; restrict_public_buckets = true }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0093](https://avd.aquasec.com/misconfig/aws-0093)
