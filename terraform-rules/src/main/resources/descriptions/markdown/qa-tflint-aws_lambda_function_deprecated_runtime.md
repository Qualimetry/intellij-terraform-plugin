The runtime attribute of aws_lambda_function must not be a deprecated runtime (e.g. nodejs10.x); use a supported runtime.

## Noncompliant Code Example

```hcl
resource "aws_lambda_function" "a" { function_name = "x"; handler = "index.handler"; runtime = "nodejs10.x"; role = "arn:aws:iam::123:role/x" }
```

## Compliant Solution

```hcl
resource "aws_lambda_function" "a" { function_name = "x"; handler = "index.handler"; runtime = "nodejs20.x"; role = "arn:aws:iam::123:role/x" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_lambda_function_deprecated_runtime.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_lambda_function_deprecated_runtime.md)
