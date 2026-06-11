Disallow `lookup(map, key)` without a default. Use native index syntax `map[key]` instead; `lookup` with two arguments has been deprecated since Terraform v0.7.

## Noncompliant Code Example

```hcl
locals { x = lookup(var.map, "key") }
```

## Compliant Solution

```hcl
locals { x = var.map["key"] }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_lookup.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_deprecated_lookup.md)
