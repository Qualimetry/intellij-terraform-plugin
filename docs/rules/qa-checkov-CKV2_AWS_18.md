# Ensure that Elastic File System (Amazon EFS) file systems are added in the backup plans of AWS Backup

`qa-checkov-CKV2_AWS_18` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that Elastic File System (Amazon EFS) file systems are added in the backup plans of AWS Backup.

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
