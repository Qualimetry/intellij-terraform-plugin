# Password authentication should be disabled on Azure virtual machines

`qa-trivy-AZU-0039` &middot; Security &middot; Vulnerability &middot; severity MAJOR

## Summary

Access to virtual machines should be authenticated using SSH keys. Removing the option of password authentication enforces more secure methods while removing the risks inherent with passwords. Use ssh authentication for virtual machines.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0039](https://avd.aquasec.com/misconfig/azu-0039)
