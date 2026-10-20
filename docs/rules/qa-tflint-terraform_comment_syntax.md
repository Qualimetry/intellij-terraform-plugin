# Terraform comment syntax

`qa-tflint-terraform_comment_syntax` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Enforce usage of `#` for comments. The Terraform language supports `#`, `//`, and `/* */`; `#` is considered idiomatic for both single and multi-line comments.

## Noncompliant code example

```hcl
// single-line comment
/* multi-line comment */
```

## Compliant solution

```hcl
# Use # for single and multi-line comments
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_comment_syntax.md](https://github.com/terraform-linters/tflint-ruleset-terraform/blob/main/docs/rules/terraform_comment_syntax.md)
