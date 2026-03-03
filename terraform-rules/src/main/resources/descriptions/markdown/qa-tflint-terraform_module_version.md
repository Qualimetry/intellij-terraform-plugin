Ensure modules from the Terraform Registry specify a `version`. Unpinned modules can pull breaking changes; constrain to a major version or exact version as needed.

## Noncompliant Code Example

```hcl
module "vpc" { source = "terraform-aws-modules/vpc/aws" }
```

## Compliant Solution

```hcl
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_version.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_module_version.md)
