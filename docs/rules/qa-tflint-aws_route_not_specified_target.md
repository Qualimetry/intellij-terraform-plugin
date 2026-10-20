# Route target not specified

`qa-tflint-aws_route_not_specified_target` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

aws_route must specify a target (e.g. gateway_id, nat_gateway_id); missing target is invalid.

## Noncompliant code example

```hcl
resource "aws_route" "a" { route_table_id = "rtb-123"; destination_cidr_block = "0.0.0.0/0" }
```

## Compliant solution

```hcl
resource "aws_route" "a" { route_table_id = "rtb-123"; destination_cidr_block = "0.0.0.0/0"; gateway_id = "igw-123" }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_route_not_specified_target.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_route_not_specified_target.md)
