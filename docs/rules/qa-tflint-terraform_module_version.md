# Module version should be specified

`qa-tflint-terraform_module_version` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Ensure modules from the Terraform Registry specify a `version`. Unpinned modules can pull breaking changes; constrain to a major version or exact version as needed.

## Noncompliant code example

```hcl
module "vpc" { source = "terraform-aws-modules/vpc/aws" }
```

## Compliant solution

```hcl
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_version.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_version.md)
