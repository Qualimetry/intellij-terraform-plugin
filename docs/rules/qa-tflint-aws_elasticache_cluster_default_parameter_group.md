# ElastiCache cluster default parameter group

`qa-tflint-aws_elasticache_cluster_default_parameter_group` &middot; Maintainability &middot; Code Smell &middot; severity MINOR

## Summary

Disallow default parameter group for ElastiCache clusters; use a custom parameter group.

## Noncompliant code example

```hcl
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "cache.t3.micro" }
```

## Compliant solution

```hcl
resource "aws_elasticache_parameter_group" "a" { family = "redis7" }
resource "aws_elasticache_cluster" "a" { cluster_id = "x"; node_type = "cache.t3.micro"; parameter_group_name = aws_elasticache_parameter_group.a.name }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_default_parameter_group.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_cluster_default_parameter_group.md)
