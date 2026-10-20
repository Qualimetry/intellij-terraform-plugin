# Synapse Workspace should have managed virtual network enabled, the default is disabled

`qa-trivy-AZU-0034` &middot; Security &middot; Vulnerability &middot; severity MINOR

## Summary

Synapse Workspace does not have managed virtual network enabled by default. When you create your Azure Synapse workspace, you can choose to associate it to a Microsoft Azure Virtual Network. The Virtual Network associated with your workspace is managed by Azure Synapse. This Virtual Network is called a Managed workspace Virtual Network. Managed private endpoints are private endpoints created in a Managed Virtual Network associated with your Azure Synapse workspace. Managed private endpoints establish a private link to Azure resources. You can only use private links in a workspace that has a Managed workspace Virtual Network. Set manage virtual network to enabled.

## Noncompliant code example

```hcl
# Non-compliant code (see documentation for examples)
```

## Compliant solution

```hcl
# Compliant code (see documentation for examples)
```

## See also

- [https://avd.aquasec.com/misconfig/azu-0034](https://avd.aquasec.com/misconfig/azu-0034)
