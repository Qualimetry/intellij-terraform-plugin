# Invalid ElastiCache cluster node type

`qa-tflint-aws_elasticache_cluster_invalid_type` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

The node_type of aws_elasticache_cluster must be a valid ElastiCache node type.

## Noncompliant code example

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "invalid" }
```

## Compliant solution

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "cache.t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_invalid_type.md)
