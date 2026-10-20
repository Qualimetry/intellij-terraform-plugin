# IAM policy document GovCloud ARNs

`qa-tflint-aws_iam_policy_document_gov_friendly_arns` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

IAM policy documents should use GovCloud-friendly ARN formats when targeting AWS GovCloud.

## Noncompliant code example

```hcl
data "aws_iam_policy_document" "a" { statement { actions = ["s3:*"]; resources = ["arn:aws:s3:::*"] } }
```

## Compliant solution

```hcl
data "aws_iam_policy_document" "a" { statement { actions = ["s3:*"]; resources = ["arn:aws-us-gov:s3:::*"] } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_document_gov_friendly_arns.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_document_gov_friendly_arns.md)
