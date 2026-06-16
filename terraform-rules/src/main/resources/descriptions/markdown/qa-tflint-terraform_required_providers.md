Require that all providers specify `source` and version constraint in `required_providers`. Avoid legacy provider block version and use the terraform block for constraints.

## Noncompliant Code Example

```hcl
terraform {}
provider "aws" { version = "~> 5.0" }
```

## Compliant Solution

```hcl
terraform {
  required_providers {
    aws = { source = "hashicorp/aws", version = "~> 5.0" }
  }
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_providers.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_providers.md)
