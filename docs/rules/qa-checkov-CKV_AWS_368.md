# Ensure Amazon Sagemaker Data Quality Job uses KMS to encrypt data on attached storage volume

`qa-checkov-CKV_AWS_368` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure Amazon Sagemaker Data Quality Job uses KMS to encrypt data on attached storage volume.

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
