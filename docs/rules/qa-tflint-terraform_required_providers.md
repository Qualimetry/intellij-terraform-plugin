# Required providers source and version

`qa-tflint-terraform_required_providers` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Require that all providers specify `source` and version constraint in `required_providers`. Avoid legacy provider block version and use the terraform block for constraints.

## Noncompliant code example

```hcl
terraform {}
provider "aws" { version = "~> 5.0" }
```

## Compliant solution

```hcl
terraform {
  required_providers {
    aws = { source = "hashicorp/aws", version = "~> 5.0" }
  }
}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_providers.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_providers.md)
