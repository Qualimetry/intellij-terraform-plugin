Disallow duplicate keys in a map. Duplicate keys are overwritten by Terraform and usually indicate a mistake.

## Noncompliant Code Example

```hcl
locals {
  m = { a = 1, a = 2 }
}
```

## Compliant Solution

```hcl
locals {
  m = { a = 1, b = 2 }
}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_map_duplicate_keys.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_map_duplicate_keys.md)
