S3 buckets should not be publicly accessible through ACL. Use private ACL (acl = "private" or aws_s3_bucket_acl with acl = "private"); avoid canned public ACLs.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "public-read" }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "private" }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0092](https://avd.aquasec.com/misconfig/aws-0092)
