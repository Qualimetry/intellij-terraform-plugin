# Terraform JSON syntax

`qa-tflint-terraform_json_syntax` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Enforce the official Terraform JSON syntax with a root object (keys: resource, variable, output, etc.). Array-at-root syntax is not the documented standard.

## Noncompliant code example

```hcl
[{"resource": {"aws_instance": {"a": {}}}}]
```

## Compliant solution

```hcl
{"resource": {"aws_instance": {"a": {}}}}
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_json_syntax.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_json_syntax.md)
