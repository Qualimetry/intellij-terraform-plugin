The node_type of aws_elasticache_cluster must be a valid ElastiCache node type.

## Noncompliant Code Example

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "invalid" }
```

## Compliant Solution

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "cache.t3.micro" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_invalid_type.md)
