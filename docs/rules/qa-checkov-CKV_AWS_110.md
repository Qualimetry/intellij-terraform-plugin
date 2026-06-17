# Ensure IAM policies does not allow privilege escalation

`qa-checkov-CKV_AWS_110` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure IAM policies does not allow privilege escalation.

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
