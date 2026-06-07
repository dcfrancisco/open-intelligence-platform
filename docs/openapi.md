# API-First Strategy

## API Domains

OIP should expose clear API domains that map to business capabilities rather than implementation details.

- Identity and session management
- Workspaces and preferences
- Conversations, prompts, and responses
- Knowledge bases, documents, and retrieval
- Agents and workflows
- Providers, models, and routing policy
- Learning, feedback, and curation
- Training datasets and fine-tune jobs
- Audit, health, and administration

This structure supports both the native UI and external clients.

## Versioning Strategy

Use URI-based major versioning such as `/api/v1/...`.

Guidelines:

- Major versions for breaking changes
- Backward-compatible additions within the same major version
- Deprecation windows documented before removal
- Contract tests to protect published behavior

This approach is simple, explicit, and practical for open-source consumers and internal product integrations.

## Authentication Strategy

Recommended API authentication:

- OIDC-based bearer tokens for user-facing clients
- Service accounts with scoped tokens for automation
- API gateway validation and downstream identity propagation
- Refresh and revocation strategy for long-lived sessions

Authorization should combine token scopes, workspace context, and RBAC checks.

## OpenAPI Standards

OIP should enforce the following standards:

- OpenAPI 3.1 for all public APIs
- Consistent error envelope with machine-readable codes
- Correlation IDs in request and response headers
- Explicit pagination, filtering, and sorting conventions
- Schema examples for key endpoints
- Security requirements declared per operation
- Idempotency support for selected write operations

## Why API-First Matters

- The UI, CLI tools, agents, and future products can all rely on the same contracts.
- External integrations remain stable as internal services evolve.
- Delivery Wizard, PortalOps AI, EventEase, and WorkTime can consume OIP capabilities without coupling to implementation details.
