aws_route should not specify more than one target type; only one of gateway_id, nat_gateway_id, etc.

## Noncompliant Code Example

```hcl
resource "aws_route" "a" { route_table_id = "rtb-123"; destination_cidr_block = "0.0.0.0/0"; gateway_id = "igw-1"; nat_gateway_id = "nat-1" }
```

## Compliant Solution

```hcl
resource "aws_route" "a" { route_table_id = "rtb-123"; destination_cidr_block = "0.0.0.0/0"; gateway_id = "igw-123" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_route_specified_multiple_targets.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_route_specified_multiple_targets.md)
