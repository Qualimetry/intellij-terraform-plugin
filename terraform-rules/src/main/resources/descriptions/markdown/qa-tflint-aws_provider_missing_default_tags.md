Require default_tags on the AWS provider so that all supported resources receive consistent tags.

## Noncompliant Code Example

```hcl
provider "aws" { region = "us-east-1" }
```

## Compliant Solution

```hcl
provider "aws" { region = "us-east-1"; default_tags { tags = { Project = "myproject" } } }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_provider_missing_default_tags.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_provider_missing_default_tags.md)
