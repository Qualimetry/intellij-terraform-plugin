S3 buckets should ignore public ACLs on buckets and objects. Use aws_s3_bucket_public_access_block with ignore_public_acls = true so that PUT calls with public ACLs are applied but the ACL is ignored.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket_public_access_block" "a" { bucket = aws_s3_bucket.a.id; block_public_acls = true; ignore_public_acls = true }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0091](https://avd.aquasec.com/misconfig/aws-0091)
