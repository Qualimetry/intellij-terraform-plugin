# Disallow IAM roles, users, and groups from using the AWS AdministratorAccess policy

`qa-checkov-CKV_AWS_274` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Disallow IAM roles, users, and groups from using the AWS AdministratorAccess policy.

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
