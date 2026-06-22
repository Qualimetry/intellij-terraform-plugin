# Terraform required_version should be specified

`qa-tflint-terraform_required_version` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

The terraform block should specify required_version (and optionally required_providers) so that the correct Terraform version is used. Without it, different users or CI may use different versions and get inconsistent behaviour or failures.

## Noncompliant code example

```hcl
terraform {}
```

## Compliant solution

```hcl
terraform {
  required_version = ">= 1.0"
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_version.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_version.md)
