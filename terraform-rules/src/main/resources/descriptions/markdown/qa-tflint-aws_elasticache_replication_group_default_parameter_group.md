Disallow default parameter group for ElastiCache replication groups.

## Noncompliant Code Example

```hcl
resource "aws_elasticache_replication_group" "a" { replication_group_id = "x"; node_type = "cache.t3.micro" }
```

## Compliant Solution

```hcl
resource "aws_elasticache_parameter_group" "a" { family = "redis7" }
resource "aws_elasticache_replication_group" "a" { replication_group_id = "x"; node_type = "cache.t3.micro"; parameter_group_name = aws_elasticache_parameter_group.a.name }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_replication_group_default_parameter_group.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_replication_group_default_parameter_group.md)
