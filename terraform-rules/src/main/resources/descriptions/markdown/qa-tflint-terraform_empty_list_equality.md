Disallow comparisons with `[]` when checking if a collection is empty. Use `length(var.list) == 0` instead; `== []` has type semantics that often make it always false.

## Noncompliant Code Example

```hcl
locals { empty = var.list == [] }
```

## Compliant Solution

```hcl
locals { empty = length(var.list) == 0 }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_empty_list_equality.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_empty_list_equality.md)
