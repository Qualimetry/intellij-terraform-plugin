Ensure S3 bucket names match naming rules (length 3–63, regex/prefix if configured). Bucket names must be globally unique.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket" "a" { bucket = "ab" }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-unique-bucket-name-123" }
```

## See Also

More information: [https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_s3_bucket_name.md](https://github.com/terraform-linters/tflint-ruleset-aws/blob/master/docs/rules/aws_s3_bucket_name.md)
