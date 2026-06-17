# Module source should be pinned

`qa-tflint-terraform_module_pinned_source` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Module source attributes should be pinned to a specific version (e.g. ref=tag or commit SHA) rather than a floating branch. Unpinned sources can cause unexpected changes when the remote is updated and make builds non-reproducible.

## Noncompliant code example

```hcl
module "m" { source = "git::https://github.com/org/repo.git?ref=main" }
```

## Compliant solution

```hcl
module "m" { source = "git::https://github.com/org/repo.git?ref=v1.0.0" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_pinned_source.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_pinned_source.md)
