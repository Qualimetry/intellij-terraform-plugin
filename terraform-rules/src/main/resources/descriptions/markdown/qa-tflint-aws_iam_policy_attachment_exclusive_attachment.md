Avoid using aws_iam_policy_attachment for multiple groups/roles/users; create separate attachments to prevent exclusive replacement.

## Noncompliant Code Example

```hcl
resource "aws_iam_policy_attachment" "a" { name = "x"; policy_arn = "arn:aws:iam::aws:policy/ReadOnly"; groups = ["g1", "g2"] }
```

## Compliant Solution

```hcl
resource "aws_iam_group_policy_attachment" "g1" { group = "g1"; policy_arn = "arn:aws:iam::aws:policy/ReadOnly" }
resource "aws_iam_group_policy_attachment" "g2" { group = "g2"; policy_arn = "arn:aws:iam::aws:policy/ReadOnly" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_attachment_exclusive_attachment.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_attachment_exclusive_attachment.md)
