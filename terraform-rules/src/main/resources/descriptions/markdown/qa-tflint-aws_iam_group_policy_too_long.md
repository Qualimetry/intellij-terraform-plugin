Inline IAM group policies have size limits; policy document should not exceed the allowed size.

## Noncompliant Code Example

```hcl
resource "aws_iam_group_policy" "a" { group = "x"; policy = jsonencode({ Version = "2012-10-17", Statement = [for i in range(100) : { Effect = "Allow", Action = "*", Resource = "*" }] }) }
```

## Compliant Solution

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Version = "2012-10-17", Statement = [{ Effect = "Allow", Action = "s3:GetObject", Resource = "*" }] }) }
resource "aws_iam_group_policy_attachment" "a" { group = "x"; policy_arn = aws_iam_policy.a.arn }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_group_policy_too_long.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_group_policy_too_long.md)
