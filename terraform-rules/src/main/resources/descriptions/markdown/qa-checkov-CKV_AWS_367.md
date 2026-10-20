# Ensure Amazon Sagemaker Data Quality Job uses KMS to encrypt model artifacts

`qa-checkov-CKV_AWS_367` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure Amazon Sagemaker Data Quality Job uses KMS to encrypt model artifacts.

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
