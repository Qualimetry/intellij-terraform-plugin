# Ensure Athena Workgroup should enforce configuration to prevent client disabling encryption

`qa-checkov-CKV_AWS_82` &middot; Security &middot; Vulnerability &middot; severity CRITICAL

## Summary

Ensure Athena Workgroup should enforce configuration to prevent client disabling encryption.

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
