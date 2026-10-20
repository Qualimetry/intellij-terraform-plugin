# Standard module structure

`qa-tflint-terraform_standard_module_structure` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Ensure a module complies with the Terraform Standard Module Structure: main.tf, variables.tf, outputs.tf; variables and outputs in their corresponding files.

## Noncompliant code example

```hcl
# All in one file or outputs in main.tf
```

## Compliant solution

```hcl
# Use main.tf, variables.tf, outputs.tf with variables and outputs in their files
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_standard_module_structure.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_standard_module_structure.md)
