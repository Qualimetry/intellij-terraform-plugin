# Ensure no aws_db_security_group resources exist

`qa-checkov-CKV_AWS_198` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure no aws_db_security_group resources exist.

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
