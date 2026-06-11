Ensure that S3 bucket has a Public Access block.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true; block_public_policy = true; ignore_public_acls = true; restrict_public_buckets = true }
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
