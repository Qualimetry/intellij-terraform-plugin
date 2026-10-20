# Ensure SQS policy does not allow public access through wildcards

`qa-checkov-CKV_AWS_387` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure SQS policy does not allow public access through wildcards.

## Noncompliant code example

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "public-read" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket_acl" "a" { bucket = aws_s3_bucket.a.id; acl = "private" }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
