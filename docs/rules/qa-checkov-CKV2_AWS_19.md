# Ensure that all EIP addresses allocated to a VPC are attached to EC2 instances

`qa-checkov-CKV2_AWS_19` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that all EIP addresses allocated to a VPC are attached to EC2 instances.

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
