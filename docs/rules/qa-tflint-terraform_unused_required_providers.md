# Unused required providers

`qa-tflint-terraform_unused_required_providers` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Check that all `required_providers` are used in the module. Remove providers that are no longer used to avoid unnecessary downloads.

## Noncompliant code example

```hcl
terraform { required_providers { aws = {}; null = {} } }
# null never used
```

## Compliant solution

```hcl
terraform { required_providers { aws = { source = "hashicorp/aws" } } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_required_providers.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_required_providers.md)
