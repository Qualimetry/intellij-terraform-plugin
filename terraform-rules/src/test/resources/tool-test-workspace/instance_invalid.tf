# Invalid instance type -> tflint aws_instance_invalid_type (requires tflint ruleset AWS)
resource "aws_instance" "bad_type" {
  ami           = "ami-12345678"
  instance_type = "t2.nano.invalid"
}
