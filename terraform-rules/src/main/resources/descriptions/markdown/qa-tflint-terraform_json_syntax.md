Enforce the official Terraform JSON syntax with a root object (keys: resource, variable, output, etc.). Array-at-root syntax is not the documented standard.

## Noncompliant Code Example

```hcl
[{"resource": {"aws_instance": {"a": {}}}}]
```

## Compliant Solution

```hcl
{"resource": {"aws_instance": {"a": {}}}}
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_json_syntax.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_json_syntax.md)
