S3 buckets should have a public access block that blocks public bucket policies. Use aws_s3_bucket_public_access_block with block_public_policy set to true.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_policy = true }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0087](https://avd.aquasec.com/misconfig/aws-0087)
