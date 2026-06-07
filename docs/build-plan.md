# Build Plan

## Purpose

This document turns the OIP architecture into a practical build sequence. The goal is to deliver a runnable MVP without losing the architectural boundaries needed for enterprise growth.

## Milestone 0: Repository Foundation

### Objective

Establish a clean open-source repository baseline with governance and documentation validation.

### Deliverables

- License
- Contribution guide
- Code of conduct
- Security policy
- GitHub Actions
- Docs validation

### Acceptance Criteria

- Repository contains the required project governance files
- Markdown validation runs in CI
- Broken local documentation links are caught automatically
- Contribution and security reporting guidance are clear

### Suggested Tests

- GitHub Actions docs workflow passes on a pull request
- Markdown link checker validates local links
- Placeholder scan returns no unresolved marker text

### Documentation Updates

- `README.md`
- `CONTRIBUTING.md`
- `SECURITY.md`
- `docs/repository-structure.md`

## Milestone 1: Backend Foundation

### Objective

Create the modular monolith backend skeleton and stable API boundaries.

### Deliverables

- Spring Boot modular monolith
- Workspace module
- Identity placeholder
- Provider registry
- Model router
- REST API structure
- OpenAPI setup

### Acceptance Criteria

- Backend starts successfully as one deployable application
- Core modules compile with clear package boundaries
- Versioned REST endpoints are available
- OpenAPI definition can be generated or served

### Suggested Tests

- Application context startup test
- REST controller smoke tests
- OpenAPI generation validation
- Module-level unit tests for router and registry contracts

### Documentation Updates

- `docs/openapi.md`
- `docs/architecture.md`
- `docs/implementation-principles.md`

## Milestone 2: Storage Foundation

### Objective

Establish durable storage and local runtime infrastructure for the MVP.

### Deliverables

- PostgreSQL
- Flyway
- `pgvector`
- Initial schema
- Docker Compose

### Acceptance Criteria

- Local environment starts PostgreSQL and required services with Docker Compose
- Flyway migrations create the initial schema successfully
- Vector extension is enabled and usable
- Backend can connect to storage in a clean startup flow

### Suggested Tests

- Migration test against a disposable PostgreSQL instance
- Repository integration tests
- Docker Compose smoke test
- Vector insert and retrieval round-trip test

### Documentation Updates

- `docs/deployment.md`
- `docs/mvp-scope.md`
- `docs/build-plan.md`

## Milestone 3: Provider Integration

### Objective

Support local and cloud inference through a common provider abstraction.

### Deliverables

- Ollama provider integration
- OpenAI-compatible provider integration
- Provider health checks
- Fallback routing
- Provider configuration

### Acceptance Criteria

- Router can invoke either configured provider
- Provider configuration can be stored and loaded safely
- Health status influences routing decisions
- Fallback works when a preferred provider is unavailable

### Suggested Tests

- Adapter contract tests
- Router policy tests
- Provider health check tests
- Fallback integration tests with mocked failures

### Documentation Updates

- `docs/model-routing.md`
- `docs/technology-stack.md`
- `docs/mvp-scope.md`

## Milestone 4: Memory Layer v1

### Objective

Implement the first working version of project memory as a platform capability.

### Deliverables

- Memory collections
- Memory entries
- Document ingestion
- Chunking
- Embeddings
- Retrieval
- Source attribution

### Acceptance Criteria

- A workspace can create and store project memory collections
- Uploaded documents produce memory entries with source lineage
- Memory entries are embedded and searchable
- Retrieved memory can be referenced by the ask flow

### Suggested Tests

- Ingestion pipeline integration tests
- Embedding creation tests
- Memory retrieval relevance tests
- Source attribution persistence tests

### Documentation Updates

- `docs/memory-layer.md`
- `docs/domain-model.md`
- `docs/knowledge-management.md`

## Milestone 5: RAG Ask Flow

### Objective

Deliver the core ask-question path that combines memory, retrieval, routing, and answer generation.

### Deliverables

- Ask-question API
- Context assembly
- Model routing
- Response generation
- Citations
- Conversation history

### Acceptance Criteria

- User can submit a question and receive a response
- Response includes citations or source references
- Ask flow uses memory and retrieval context
- Conversation history is stored and queryable

### Suggested Tests

- End-to-end ask flow integration test
- Context assembly unit tests
- Citation formatting tests
- Conversation persistence tests

### Documentation Updates

- `docs/rag-architecture.md`
- `docs/mvp.md`
- `docs/mvp-scope.md`

## Milestone 6: Frontend MVP

### Objective

Build the first usable UI for the MVP workflow.

### Deliverables

- Next.js app
- Workspace selector
- Provider settings
- Document upload
- Chat UI
- Memory browser

### Acceptance Criteria

- User can open the UI and complete the MVP flow without manual API calls
- Workspace selection affects visible data
- User can upload documents and ask questions from the UI
- Memory browser exposes stored project memory records

### Suggested Tests

- Frontend component tests
- UI-to-API integration tests
- Upload and chat smoke tests
- Manual end-to-end browser validation

### Documentation Updates

- `README.md`
- `docs/mvp-scope.md`
- `docs/deployment.md`

## Milestone 7: Governance Foundation

### Objective

Add the minimum governance controls needed to operate the MVP responsibly.

### Deliverables

- Audit logging
- Usage tracking
- Token tracking
- Basic quotas
- Secrets handling

### Acceptance Criteria

- AI requests produce usage and audit records
- Provider credentials are not hard-coded
- Token usage is recorded per workspace or request context
- Basic quota or usage guardrails can block excessive usage

### Suggested Tests

- Audit event persistence tests
- Usage accounting tests
- Quota enforcement tests
- Secret configuration loading tests

### Documentation Updates

- `docs/security.md`
- `docs/observability.md`
- `docs/risk-register.md`

## Milestone 8: Enterprise Readiness

### Objective

Prepare the platform for controlled enterprise deployment beyond the MVP.

### Deliverables

- OIDC integration
- RBAC
- Workspace isolation
- Observability
- Backup and restore
- Deployment profiles

### Acceptance Criteria

- Platform can authenticate through an OIDC provider
- Workspace access is enforced consistently
- Operational dashboards and traces are available
- Backup and restore process is documented and testable
- Deployment profiles distinguish local and enterprise runtime expectations

### Suggested Tests

- OIDC integration tests
- Authorization tests
- Backup and restore drill
- Deployment profile smoke tests

### Documentation Updates

- `docs/security.md`
- `docs/deployment.md`
- `docs/observability.md`
- `docs/architecture.md`
