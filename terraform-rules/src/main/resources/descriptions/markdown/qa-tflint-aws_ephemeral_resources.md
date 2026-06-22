# Ephemeral resources

`qa-tflint-aws_ephemeral_resources` &middot; Maintainability &middot; Code Smell &middot; severity MAJOR

## Summary

Disallow configuration that uses ephemeral storage or instance store in a way that can cause data loss.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-x"; ephemeral_block_device { device_name = "/dev/sdb" } }
```

## Compliant solution

```hcl
resource "aws_instance" "a" { ami = "ami-x"; root_block_device { volume_size = 20 } }
```

## See also

- [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_ephemeral_resources.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_ephemeral_resources.md)
