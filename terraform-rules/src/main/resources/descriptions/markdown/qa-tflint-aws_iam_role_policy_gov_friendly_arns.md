# IAM role policy GovCloud-friendly ARNs

`qa-tflint-aws_iam_role_policy_gov_friendly_arns` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

IAM role inline policies should use GovCloud-friendly ARN formats when applicable.

## Noncompliant code example

```hcl
resource "aws_iam_role_policy" "a" { role = "x"; policy = jsonencode({ Statement = [{ Resource = "arn:aws:s3:::*" }] }) }
```

## Compliant solution

```hcl
resource "aws_iam_role_policy" "a" { role = "x"; policy = jsonencode({ Statement = [{ Resource = "arn:aws-us-gov:s3:::*" }] }) }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_role_policy_gov_friendly_arns.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_role_policy_gov_friendly_arns.md)
