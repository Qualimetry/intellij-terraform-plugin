# Ensure CodeBuild project environments have a logging configuration

`qa-checkov-CKV_AWS_314` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure CodeBuild project environments have a logging configuration.

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
