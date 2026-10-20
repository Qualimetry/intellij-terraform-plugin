# Lambda deprecated runtime

`qa-tflint-aws_lambda_function_deprecated_runtime` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

The runtime attribute of aws_lambda_function must not be a deprecated runtime (e.g. nodejs10.x); use a supported runtime.

## Noncompliant code example

```hcl
resource "aws_lambda_function" "a" { function_name = "x"; handler = "index.handler"; runtime = "nodejs10.x"; role = "arn:aws:iam::123:role/x" }
```

## Compliant solution

```hcl
resource "aws_lambda_function" "a" { function_name = "x"; handler = "index.handler"; runtime = "nodejs20.x"; role = "arn:aws:iam::123:role/x" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_lambda_function_deprecated_runtime.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_lambda_function_deprecated_runtime.md)
