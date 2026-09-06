# Invalid ElastiCache replication group node type

`qa-tflint-aws_elasticache_replication_group_invalid_type` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

The node_type must be a valid ElastiCache replication group node type.

## Noncompliant code example

```hcl
resource "aws_elasticache_replication_group" "a" { replication_group_id = "x"; node_type = "invalid" }
```

## Compliant solution

```hcl
resource "aws_elasticache_replication_group" "a" { replication_group_id = "x"; node_type = "cache.t3.micro" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_replication_group_invalid_type.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_elasticache_replication_group_invalid_type.md)
