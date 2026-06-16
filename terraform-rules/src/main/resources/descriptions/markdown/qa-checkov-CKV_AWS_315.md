Ensure EC2 Auto Scaling groups use EC2 launch templates.

## Noncompliant Code Example

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
```

## Compliant Solution

```hcl
resource "aws_s3_bucket" "a" { bucket = "my-bucket" }
# Add the required configuration per Checkov policy (e.g. encryption, logging, versioning).
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
