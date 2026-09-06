# Ensure that load balancer is using at least TLS 1.2

`qa-checkov-CKV_AWS_103` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that load balancer is using at least TLS 1.2.

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
