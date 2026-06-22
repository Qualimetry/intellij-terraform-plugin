# Ensure DynamoDB table replica KMS encryption uses CMK

`qa-checkov-CKV_AWS_271` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure DynamoDB table replica KMS encryption uses CMK.

## Noncompliant code example

```hcl
resource "aws_ebs_volume" "a" { size = 10; availability_zone = "us-east-1a" }
```

## Compliant solution

```hcl
resource "aws_ebs_volume" "a" { size = 10; availability_zone = "us-east-1a"; encrypted = true }
```

## See also

- [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
