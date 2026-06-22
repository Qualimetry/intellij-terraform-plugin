# Ensure AWS Lambda function is configured to validate code-signing

`qa-checkov-CKV_AWS_272` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure AWS Lambda function is configured to validate code-signing.

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
