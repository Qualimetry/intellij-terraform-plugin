Enforce usage of `#` for comments. The Terraform language supports `#`, `//`, and `/* */`; `#` is considered idiomatic for both single and multi-line comments.

## Noncompliant Code Example

```hcl
// single-line comment
/* multi-line comment */
```

## Compliant Solution

```hcl
# Use # for single and multi-line comments
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_comment_syntax.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_comment_syntax.md)
