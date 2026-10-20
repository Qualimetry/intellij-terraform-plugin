# IAM policy SID invalid characters

`qa-tflint-aws_iam_policy_sid_invalid_characters` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Policy statement IDs (Sid) must contain only alphanumeric and underscore characters.

## Noncompliant code example

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Statement = [{ Sid = "allow-all!" }] }) }
```

## Compliant solution

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Statement = [{ Sid = "AllowAll" }] }) }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_sid_invalid_characters.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_sid_invalid_characters.md)
