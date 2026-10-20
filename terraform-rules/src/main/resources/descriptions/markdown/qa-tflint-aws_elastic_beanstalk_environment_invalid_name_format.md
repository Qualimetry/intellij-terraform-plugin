# Invalid Elastic Beanstalk environment name

`qa-tflint-aws_elastic_beanstalk_environment_invalid_name_format` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Elastic Beanstalk environment names must match the required format (length and character rules).

## Noncompliant code example

```hcl
resource "aws_elastic_beanstalk_environment" "a" { name = "ab" }
```

## Compliant solution

```hcl
resource "aws_elastic_beanstalk_environment" "a" { name = "my-env-name" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elastic_beanstalk_environment_invalid_name_format.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elastic_beanstalk_environment_invalid_name_format.md)
