# Module shallow clone

`qa-tflint-terraform_module_shallow_clone` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Require pinned Git-hosted modules to use shallow cloning. Add `depth=1` to the source URL to reduce download size and improve CI performance.

## Noncompliant code example

```hcl
module "m" { source = "git::https://github.com/org/repo.git?ref=v1.0.0" }
```

## Compliant solution

```hcl
module "m" { source = "git::https://github.com/org/repo.git?ref=v1.0.0//subdir" }
# Add depth=1 to the Git URL when using a ref
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_shallow_clone.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_shallow_clone.md)
