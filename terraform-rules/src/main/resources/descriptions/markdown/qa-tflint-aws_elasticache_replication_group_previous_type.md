Disallow previous-generation node types for replication groups.

## Noncompliant Code Example

```hcl
resource "aws_elasticache_replication_group" "a" { replication_group_id = "x"; node_type = "cache.t1.micro" }
```

## Compliant Solution

```hcl
resource "aws_elasticache_replication_group" "a" { replication_group_id = "x"; node_type = "cache.t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_replication_group_previous_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_replication_group_previous_type.md)
