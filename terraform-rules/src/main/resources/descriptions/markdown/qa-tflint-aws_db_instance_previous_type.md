Disallow previous-generation RDS instance classes; use current generation for performance and support.

## Noncompliant Code Example

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t1.micro" }
```

## Compliant Solution

```hcl
resource "aws_db_instance" "a" { engine = "postgres"; instance_class = "db.t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_previous_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_db_instance_previous_type.md)
