# S3 Access block should block public ACL

`qa-trivy-AWS-0086` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

S3 buckets should have a public access block that blocks public ACLs. Use aws_s3_bucket_public_access_block with block_public_acls set to true so that public ACLs do not grant unintended access.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true; block_public_policy = true; ignore_public_acls = true; restrict_public_buckets = true }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0086](https://avd.aquasec.com/misconfig/aws-0086)
