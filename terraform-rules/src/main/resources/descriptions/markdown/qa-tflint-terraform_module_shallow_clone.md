Require pinned Git-hosted modules to use shallow cloning. Add `depth=1` to the source URL to reduce download size and improve CI performance.

## Noncompliant Code Example

```hcl
module "m" { source = "git::https://github.com/org/repo.git?ref=v1.0.0" }
```

## Compliant Solution

```hcl
module "m" { source = "git::https://github.com/org/repo.git?ref=v1.0.0//subdir" }
# Add depth=1 to the Git URL when using a ref
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_shallow_clone.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_shallow_clone.md)
