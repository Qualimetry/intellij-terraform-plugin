# Ensure AWS GitHub Actions OIDC authorization policies only allow safe claims and claim order

`qa-checkov-CKV_AWS_358` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure AWS GitHub Actions OIDC authorization policies only allow safe claims and claim order.

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
