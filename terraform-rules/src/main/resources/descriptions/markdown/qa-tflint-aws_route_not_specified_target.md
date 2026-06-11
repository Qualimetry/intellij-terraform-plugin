aws_route must specify a target (e.g. gateway_id, nat_gateway_id); missing target is invalid.

## Noncompliant Code Example

```hcl
resource "aws_route" "a" { route_table_id = "rtb-123"; destination_cidr_block = "0.0.0.0/0" }
```

## Compliant Solution

```hcl
resource "aws_route" "a" { route_table_id = "rtb-123"; destination_cidr_block = "0.0.0.0/0"; gateway_id = "igw-123" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_route_not_specified_target.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_route_not_specified_target.md)
