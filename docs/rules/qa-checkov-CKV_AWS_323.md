# Ensure ElastiCache clusters do not use the default subnet group

`qa-checkov-CKV_AWS_323` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure ElastiCache clusters do not use the default subnet group.

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
