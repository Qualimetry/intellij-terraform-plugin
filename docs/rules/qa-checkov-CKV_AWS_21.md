# Ensure all data stored in the S3 bucket have versioning enabled

`qa-checkov-CKV_AWS_21` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Ensure all data stored in the S3 bucket have versioning enabled.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
# Add the required configuration per Checkov policy (e.g. encryption, logging, versioning).
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
