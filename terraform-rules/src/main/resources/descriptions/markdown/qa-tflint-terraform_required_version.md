The terraform block should specify required_version (and optionally required_providers) so that the correct Terraform version is used. Without it, different users or CI may use different versions and get inconsistent behaviour or failures.

## Noncompliant Code Example

```hcl
terraform {}
```

## Compliant Solution

```hcl
terraform {
  required_version = ">= 1.0"
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_version.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_required_version.md)
