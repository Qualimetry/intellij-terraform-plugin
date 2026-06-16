Ensure a module complies with the Terraform Standard Module Structure: main.tf, variables.tf, outputs.tf; variables and outputs in their corresponding files.

## Noncompliant Code Example

```hcl
# All in one file or outputs in main.tf
```

## Compliant Solution

```hcl
# Use main.tf, variables.tf, outputs.tf with variables and outputs in their files
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_standard_module_structure.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_standard_module_structure.md)
