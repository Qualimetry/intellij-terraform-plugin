# Ensure ECS Cluster logging is enabled and client to container communication uses CMK

`qa-checkov-CKV_AWS_224` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure ECS Cluster logging is enabled and client to container communication uses CMK.

## Noncompliant code example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket"; logging { target_bucket = aws_s3_bucket.logs.id; target_prefix = "log/" } }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
