VPCs should have flow logging enabled for network traffic monitoring and compliance. Create aws_flow_log resources for the VPC.

## Noncompliant Code Example

```hcl
resource "aws_vpc" "a" { cidr_block = "10.0.0.0/16" }
```

## Compliant Solution

```hcl
resource "aws_vpc" "a" { cidr_block = "10.0.0.0/16" }
resource "aws_flow_log" "a" { vpc_id = aws_vpc.a.id; traffic_type = "ALL"; log_destination_type = "cloud-watch-logs"; log_destination = aws_cloudwatch_log_group.a.arn }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0178](https://avd.aquasec.com/misconfig/aws-0178)
