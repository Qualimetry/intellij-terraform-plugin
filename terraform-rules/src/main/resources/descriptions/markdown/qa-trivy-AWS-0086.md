S3 buckets should have a public access block that blocks public ACLs. Use aws_s3_bucket_public_access_block with block_public_acls set to true so that public ACLs do not grant unintended access.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true; block_public_policy = true; ignore_public_acls = true; restrict_public_buckets = true }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0086](https://avd.aquasec.com/misconfig/aws-0086)
