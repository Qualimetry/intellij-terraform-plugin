IAM policy documents should use GovCloud-friendly ARN formats when targeting AWS GovCloud.

## Noncompliant Code Example

```hcl
data "aws_iam_policy_document" "a" { statement { actions = ["s3:*"]; resources = ["arn:aws:s3:::*"] } }
```

## Compliant Solution

```hcl
data "aws_iam_policy_document" "a" { statement { actions = ["s3:*"]; resources = ["arn:aws-us-gov:s3:::*"] } }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_document_gov_friendly_arns.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_document_gov_friendly_arns.md)
