Disallow invalid or deprecated ACM certificate lifecycle configuration.

## Noncompliant Code Example

```hcl
resource "aws_acm_certificate" "a" { lifecycle { create_before_destroy = false } }
```

## Compliant Solution

```hcl
resource "aws_acm_certificate" "a" { lifecycle { create_before_destroy = true } }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_acm_certificate_lifecycle.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_acm_certificate_lifecycle.md)
