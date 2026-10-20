# Ensure that CloudWatch Log Group specifies retention days

`qa-checkov-CKV_AWS_66` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure that CloudWatch Log Group specifies retention days.

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
