Elastic Beanstalk environment names must match the required format (length and character rules).

## Noncompliant Code Example

```hcl
resource "aws_elastic_beanstalk_environment" "a" { name = "ab" }
```

## Compliant Solution

```hcl
resource "aws_elastic_beanstalk_environment" "a" { name = "my-env-name" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elastic_beanstalk_environment_invalid_name_format.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elastic_beanstalk_environment_invalid_name_format.md)
