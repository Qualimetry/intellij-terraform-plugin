# Ensure no IAM policies documents allow ALL or any AWS principal permissions to the resource

`qa-checkov-CKV_AWS_283` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Ensure no IAM policies documents allow ALL or any AWS principal permissions to the resource.

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
