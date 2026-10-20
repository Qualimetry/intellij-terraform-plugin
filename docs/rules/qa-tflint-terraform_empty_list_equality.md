# Empty list equality check

`qa-tflint-terraform_empty_list_equality` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Disallow comparisons with `[]` when checking if a collection is empty. Use `length(var.list) == 0` instead; `== []` has type semantics that often make it always false.

## Noncompliant code example

```hcl
locals { empty = var.list == [] }
```

## Compliant solution

```hcl
locals { empty = length(var.list) == 0 }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_empty_list_equality.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_empty_list_equality.md)
