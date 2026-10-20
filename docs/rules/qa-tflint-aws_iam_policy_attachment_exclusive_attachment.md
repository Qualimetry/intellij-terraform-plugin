# IAM policy attachment exclusive

`qa-tflint-aws_iam_policy_attachment_exclusive_attachment` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Avoid using aws_iam_policy_attachment for multiple groups/roles/users; create separate attachments to prevent exclusive replacement.

## Noncompliant code example

```hcl
resource "aws_iam_policy_attachment" "a" { name = "x"; policy_arn = "arn:aws:iam::aws:policy/ReadOnly"; groups = ["g1", "g2"] }
```

## Compliant solution

```hcl
resource "aws_iam_group_policy_attachment" "g1" { group = "g1"; policy_arn = "arn:aws:iam::aws:policy/ReadOnly" }
resource "aws_iam_group_policy_attachment" "g2" { group = "g2"; policy_arn = "arn:aws:iam::aws:policy/ReadOnly" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_attachment_exclusive_attachment.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_attachment_exclusive_attachment.md)
