# No public access with ACL

`qa-trivy-AWS-0092` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

S3 buckets should not be publicly accessible through ACL. Use private ACL (acl = "private" or aws_s3_bucket_acl with acl = "private"); avoid canned public ACLs.

## Noncompliant code example

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "public-read" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "private" }
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0092](https://avd.aquasec.com/misconfig/aws-0092)
