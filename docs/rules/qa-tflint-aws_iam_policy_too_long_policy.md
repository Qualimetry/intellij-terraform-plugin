# IAM policy too long

`qa-tflint-aws_iam_policy_too_long_policy` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Inline IAM policy document exceeds size limits; consider using managed policies or splitting.

## Noncompliant code example

```hcl
resource "aws_iam_role_policy" "a" { role = "x"; policy = jsonencode({ Version = "2012-10-17", Statement = [for i in range(50) : { Effect = "Allow", Action = "*", Resource = "*" }] }) }
```

## Compliant solution

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Version = "2012-10-17", Statement = [{ Effect = "Allow", Action = "s3:GetObject", Resource = "*" }] }) }
resource "aws_iam_role_policy_attachment" "a" { role = "x"; policy_arn = aws_iam_policy.a.arn }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_too_long_policy.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_too_long_policy.md)
