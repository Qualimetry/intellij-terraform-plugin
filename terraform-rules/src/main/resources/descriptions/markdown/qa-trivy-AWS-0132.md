EC2 instances should use secure metadata service configuration (e.g. IMDSv2, hop limit) to reduce risk of metadata disclosure.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro"; metadata_options { http_tokens = "required"; http_put_response_hop_limit = 1 } }
```

## See Also

More information: [https://avd.aquasec.com/misconfig/aws-0132](https://avd.aquasec.com/misconfig/aws-0132)
