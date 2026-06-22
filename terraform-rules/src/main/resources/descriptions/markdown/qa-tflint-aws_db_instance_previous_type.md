# Previous generation RDS instance type

`qa-tflint-aws_db_instance_previous_type` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow previous-generation RDS instance classes; use current generation for performance and support.

## Noncompliant code example

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t1.micro" }
```

## Compliant solution

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_previous_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_previous_type.md)
