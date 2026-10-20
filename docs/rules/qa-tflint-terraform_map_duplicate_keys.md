# Duplicate map keys

`qa-tflint-terraform_map_duplicate_keys` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Disallow duplicate keys in a map. Duplicate keys are overwritten by Terraform and usually indicate a mistake.

## Noncompliant code example

```hcl
locals {
  m = { a = 1, a = 2 }
}
```

## Compliant solution

```hcl
locals {
  m = { a = 1, b = 2 }
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_map_duplicate_keys.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_map_duplicate_keys.md)
