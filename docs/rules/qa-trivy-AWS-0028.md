# Enforce HTTP Token IMDS

`qa-trivy-AWS-0028` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

aws_instance should activate session tokens for Instance Metadata Service (IMDS v2). Set metadata_options.http_tokens to "required" to improve security when talking to IMDS; default is optional.

## Noncompliant code example

```hcl
resource "aws_instance" "a" { ami = "ami-123"; instance_type = "t3.micro" }
```

## Compliant solution

```hcl
resource "aws_instance" "a" {
  ami = "ami-123"
  instance_type = "t3.micro"
  metadata_options { http_tokens = "required" }
}
```

## See also

- [https://avd.aquasec.com/misconfig/aws-0028](https://avd.aquasec.com/misconfig/aws-0028)
