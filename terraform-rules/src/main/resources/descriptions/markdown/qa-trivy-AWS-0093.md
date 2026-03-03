S3 Access block should restrict public bucket to limit access. Set restrict_public_buckets = true on aws_s3_bucket_public_access_block so that only the bucket owner and AWS services can access when a public policy exists.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; restrict_public_buckets = true }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0093](https://avd.aquasec.com/misconfig/aws-0093)
