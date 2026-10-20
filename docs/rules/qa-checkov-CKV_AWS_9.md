# Ensure IAM password policy expires passwords within 90 days or less

`qa-checkov-CKV_AWS_9` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure IAM password policy expires passwords within 90 days or less.

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
