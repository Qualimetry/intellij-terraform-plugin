Disallow configuration that uses ephemeral storage or instance store in a way that can cause data loss.

## Noncompliant Code Example

```hcl
resource "aws_instance" "a" { ami = "ami-x"; ephemeral_block_device { device_name = "/dev/sdb" } }
```

## Compliant Solution

```hcl
resource "aws_instance" "a" { ami = "ami-x"; root_block_device { volume_size = 20 } }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_ephemeral_resources.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_ephemeral_resources.md)
