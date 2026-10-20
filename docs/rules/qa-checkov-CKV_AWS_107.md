# Ensure IAM policies does not allow credentials exposure

`qa-checkov-CKV_AWS_107` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure IAM policies does not allow credentials exposure.

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
