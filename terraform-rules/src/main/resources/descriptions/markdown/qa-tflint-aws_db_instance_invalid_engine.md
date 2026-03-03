The engine attribute of aws_db_instance must be a valid RDS engine (e.g. mysql, postgres). Invalid values cause apply failures.

## Noncompliant Code Example

```hcl
resource "aws_db_instance" "a" { engine = "invalid"; instance_class = "db.t3.micro" }
```

## Compliant Solution

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_invalid_engine.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_invalid_engine.md)
