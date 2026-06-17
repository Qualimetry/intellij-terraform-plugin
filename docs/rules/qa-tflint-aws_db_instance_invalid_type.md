# Invalid RDS DB instance type

`qa-tflint-aws_db_instance_invalid_type` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

The instance_class of aws_db_instance must be a valid RDS instance type. Invalid or previous-generation types cause apply failures.

## Noncompliant code example

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.invalid" }
```

## Compliant solution

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_invalid_type.md)
