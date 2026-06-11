Check that all `required_providers` are used in the module. Remove providers that are no longer used to avoid unnecessary downloads.

## Noncompliant Code Example

```hcl
terraform { required_providers { aws = {}; null = {} } }
# null never used
```

## Compliant Solution

```hcl
terraform { required_providers { aws = { source = "hashicorp/aws" } } }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_required_providers.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_unused_required_providers.md)
