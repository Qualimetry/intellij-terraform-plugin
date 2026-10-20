# Previous generation ElastiCache node type

`qa-tflint-aws_elasticache_cluster_previous_type` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow previous-generation ElastiCache node types.

## Noncompliant code example

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "cache.t1.micro" }
```

## Compliant solution

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "cache.t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_previous_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_previous_type.md)
