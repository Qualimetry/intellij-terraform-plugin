# Ensure an IAM User does not have access to the console

`qa-checkov-CKV2_AWS_22` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure an IAM User does not have access to the console.

## Noncompliant code example

```hcl
data "aws_iam_policy_document" "a" { statement { actions = ["*"]; resources = ["*"] } }
```

## Compliant solution

```hcl
data "aws_iam_policy_document" "a" { statement { actions = ["s3:GetObject"]; resources = ["arn:aws:s3:::bucket/*"] } }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
