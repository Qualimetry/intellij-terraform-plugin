# Ensure Connect Instance Kinesis Video Stream Storage Config uses CMK

`qa-checkov-CKV_AWS_269` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure Connect Instance Kinesis Video Stream Storage Config uses CMK.

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
