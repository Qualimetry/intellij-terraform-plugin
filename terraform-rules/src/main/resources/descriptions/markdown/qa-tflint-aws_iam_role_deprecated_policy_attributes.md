# IAM role deprecated policy attributes

`qa-tflint-aws_iam_role_deprecated_policy_attributes` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow deprecated attributes on IAM role or inline role policies.

## Noncompliant code example

```hcl
resource "aws_iam_role" "a" { assume_role_policy = "{}"; name = "x" }
```

## Compliant solution

```hcl
resource "aws_iam_role" "a" { assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [] }); name = "x" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_role_deprecated_policy_attributes.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_role_deprecated_policy_attributes.md)
