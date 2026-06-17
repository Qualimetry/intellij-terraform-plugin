# AWS provider default tags

`qa-tflint-aws_provider_missing_default_tags` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Require default_tags on the AWS provider so that all supported resources receive consistent tags.

## Noncompliant code example

```hcl
provider "aws" { region = "us-east-1" }
```

## Compliant solution

```hcl
provider "aws" { region = "us-east-1"; default_tags { tags = { Project = "myproject" } } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_provider_missing_default_tags.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_provider_missing_default_tags.md)
