# Ensure WAF prevents message lookup in Log4j2. See CVE-2021-44228 aka log4jshell

`qa-checkov-CKV_AWS_192` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure WAF prevents message lookup in Log4j2. See CVE-2021-44228 aka log4jshell.

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
