# Ensure that an S3 bucket has a lifecycle configuration

`qa-checkov-CKV2_AWS_61` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that an S3 bucket has a lifecycle configuration.

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
