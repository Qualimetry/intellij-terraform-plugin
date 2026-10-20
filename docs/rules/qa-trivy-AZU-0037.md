# Ensure that no sensitive credentials are exposed in VM custom_data

`qa-trivy-AZU-0037` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

When creating Azure Virtual Machines, custom_data is used to pass start up information into the virtual machine. This custom_data must not contain access key credentials. Don't use sensitive credentials in the VM custom_data.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0037](https://avd.aquasec.com/misconfig/azu-0037)
