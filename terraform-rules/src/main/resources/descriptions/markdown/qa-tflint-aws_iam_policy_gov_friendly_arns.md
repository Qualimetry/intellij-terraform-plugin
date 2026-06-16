IAM policies should use GovCloud-friendly ARN formats where applicable.

## Noncompliant Code Example

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Statement = [{ Resource = "arn:aws:s3:::*" }] }) }
```

## Compliant Solution

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Statement = [{ Resource = "arn:aws-us-gov:s3:::*" }] }) }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_gov_friendly_arns.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_gov_friendly_arns.md)
