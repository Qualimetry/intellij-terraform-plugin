Ensure at least two approving reviews for PRs.

## Noncompliant Code Example

```hcl
resource "azuredevops_git_repository" "a" { name = "repo"; project_id = azuredevops_project.p.id; initialization { init_type = "Uninitialized" } }
```

## Compliant Solution

```hcl
resource "azuredevops_git_repository" "a" { name = "repo"; project_id = azuredevops_project.p.id; initialization { init_type = "Uninitialized" } }
# Configure branch policies (e.g. minimum reviewers) per Checkov policy.
```

## See Also

More information: [https://www.checkov.io/5.Policy%20Index/terraform.html](https://www.checkov.io/5.Policy%20Index/terraform.html)
