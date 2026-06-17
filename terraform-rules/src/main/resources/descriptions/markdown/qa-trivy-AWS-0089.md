# S3 buckets should have logging enabled

`qa-trivy-AWS-0089` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

S3 buckets should have server access logging enabled. Add a logging block to the bucket resource pointing to a target bucket for log delivery.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket"; logging { target_bucket = aws_s3_bucket.logs.id; target_prefix = "log/" } }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0089](https://avd.aquasec.com/misconfig/aws-0089)
