Disallow deprecated attributes on IAM role or inline role policies.

## Noncompliant Code Example

```hcl
resource "aws_iam_role" "a" { assume_role_policy = "{}"; name = "x" }
```

## Compliant Solution

```hcl
resource "aws_iam_role" "a" { assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [] }); name = "x" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_role_deprecated_policy_attributes.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_role_deprecated_policy_attributes.md)
