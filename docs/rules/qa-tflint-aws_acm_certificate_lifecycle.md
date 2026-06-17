# ACM certificate lifecycle

`qa-tflint-aws_acm_certificate_lifecycle` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow invalid or deprecated ACM certificate lifecycle configuration.

## Noncompliant code example

```hcl
resource "aws_acm_certificate" "a" { lifecycle { create_before_destroy = false } }
```

## Compliant solution

```hcl
resource "aws_acm_certificate" "a" { lifecycle { create_before_destroy = true } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_acm_certificate_lifecycle.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_acm_certificate_lifecycle.md)
