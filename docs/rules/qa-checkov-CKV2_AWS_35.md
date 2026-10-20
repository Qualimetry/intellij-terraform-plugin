# AWS NAT Gateways should be utilized for the default route

`qa-checkov-CKV2_AWS_35` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

AWS NAT Gateways should be utilized for the default route.

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
