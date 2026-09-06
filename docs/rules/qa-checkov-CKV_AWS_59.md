# Ensure there is no open access to back-end resources through API

`qa-checkov-CKV_AWS_59` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure there is no open access to back-end resources through API.

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
