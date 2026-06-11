The instance_class of aws_db_instance must be a valid RDS instance type. Invalid or previous-generation types cause apply failures.

## Noncompliant Code Example

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.invalid" }
```

## Compliant Solution

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_invalid_type.md)
