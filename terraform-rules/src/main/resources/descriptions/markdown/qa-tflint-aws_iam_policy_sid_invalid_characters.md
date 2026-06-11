Policy statement IDs (Sid) must contain only alphanumeric and underscore characters.

## Noncompliant Code Example

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Statement = [{ Sid = "allow-all!" }] }) }
```

## Compliant Solution

```hcl
resource "aws_iam_policy" "a" { policy = jsonencode({ Statement = [{ Sid = "AllowAll" }] }) }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_sid_invalid_characters.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_iam_policy_sid_invalid_characters.md)
