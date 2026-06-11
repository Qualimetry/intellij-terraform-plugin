Disallow use of default parameter group for RDS DB instances; use a custom parameter group for control.

## Noncompliant Code Example

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro" }
```

## Compliant Solution

```hcl
resource "aws_db_parameter_group" "a" { family = "postgres14" }
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro"; parameter_group_name = aws_db_parameter_group.a.name }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_default_parameter_group.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_default_parameter_group.md)
