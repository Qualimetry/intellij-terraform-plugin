# Ensure IAM password policy requires minimum length of 14 or greater

`qa-checkov-CKV_AWS_10` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure IAM password policy requires minimum length of 14 or greater.

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
