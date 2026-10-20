# Deprecated lookup usage

`qa-tflint-terraform_deprecated_lookup` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow `lookup(map, key)` without a default. Use native index syntax `map[key]` instead; `lookup` with two arguments has been deprecated since Terraform v0.7.

## Noncompliant code example

```hcl
locals { x = lookup(var.map, "key") }
```

## Compliant solution

```hcl
locals { x = var.map["key"] }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_lookup.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_lookup.md)
