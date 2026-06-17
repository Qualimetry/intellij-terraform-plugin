# Intentionally non-compliant Terraform to trigger tflint, tfsec, and checkov rules.
# No terraform { required_version } block -> tflint terraform_required_version
# Resource name camelCase -> tflint terraform_naming_convention
# S3 bucket: no encryption, no logging, public ACL -> tfsec AWS001, AWS002, AWS017; checkov CKV_AWS_20, CKV2_AWS_6, CKV_AWS_57

resource "aws_s3_bucket" "myBucket" {
  bucket = "test-bucket-validation"

  acl = "public-read"
}

resource "aws_s3_bucket_public_access_block" "example" {
  bucket = aws_s3_bucket.myBucket.id
}
