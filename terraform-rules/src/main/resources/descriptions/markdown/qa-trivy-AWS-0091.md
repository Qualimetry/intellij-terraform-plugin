# S3 Access block should ignore public ACLs

`qa-trivy-AWS-0091` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

S3 buckets should ignore public ACLs on buckets and objects. Use aws_s3_bucket_public_access_block with ignore_public_acls = true so that PUT calls with public ACLs are applied but the ACL is ignored.

## Noncompliant code example

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true; ignore_public_acls = true }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0091](https://avd.aquasec.com/misconfig/aws-0091)
