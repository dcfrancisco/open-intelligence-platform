# Build Plan

## Purpose

This document turns the OIP architecture into a practical build sequence.

The current validated path is:

```text
Continue -> OIP -> Ollama
```

The near-term goal is to complete the first assistant-facing runtime before expanding memory, knowledge, and the broader control plane.

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

## Milestone 1: OpenAI-Compatible API

### Objective

Expose OIP as an OpenAI-compatible assistant backend.

### Deliverables

- `GET /v1/models`
- `POST /v1/chat/completions`
- OpenAPI publication
- Swagger UI
- Spring Boot assistant API boundary

### Acceptance Criteria

- OIP serves an OpenAI-compatible model list
- OIP serves OpenAI-compatible chat completions
- OpenAPI definition is published
- Swagger UI is enabled

### Suggested Tests

- Controller tests for `/v1/models`
- Controller tests for `/v1/chat/completions`
- OpenAPI generation validation
- Response schema validation for OpenAI-compatible responses

### Documentation Updates

- `docs/openapi.md`
- `docs/architecture.md`
- `docs/mvp.md`

## Milestone 2: Ollama Integration

### Objective

Route OIP assistant requests to local inference through `Ollama`.

### Deliverables

- `Ollama` provider integration
- Model discovery
- Chat completion routing
- Local-first default model execution

### Acceptance Criteria

- OIP can discover locally available models
- OIP can send chat completions to `Ollama`
- Returned responses are OpenAI-compatible
- Local models are the preferred default runtime

### Suggested Tests

- Adapter contract tests
- Provider health checks
- Local model discovery tests
- Chat completion routing tests

### Documentation Updates

- `docs/model-routing.md`
- `docs/mvp.md`
- `docs/technology-stack.md`

## Milestone 3: Continue Integration

### Objective

Validate `Continue` as the first assistant client adapter into OIP.

### Deliverables

- `Continue` configuration guidance
- OIP alias exposure for assistant clients
- End-to-end `Continue -> OIP -> Ollama` validation

### Acceptance Criteria

- `Continue` can call `GET /v1/models`
- `Continue` can call `POST /v1/chat/completions`
- `Continue` receives responses through OIP
- The assistant client does not need direct `Ollama` configuration

### Suggested Tests

- End-to-end manual validation with `Continue`
- Assistant configuration smoke test
- Response compatibility test against real client expectations

### Documentation Updates

- `docs/vision.md`
- `docs/mvp.md`
- `README.md`

## Milestone 4: Modes

### Objective

Introduce OIP runtime modes without implementing full multi-agent orchestration.

### Deliverables

- `CHAT`
- `PLAN`
- `AGENT`
- Internal mode resolution
- Prompt- and routing-based behavior differences

### Acceptance Criteria

- OIP can resolve requests to runtime modes
- Modes affect prompt construction or routing behavior
- Assistant clients can target stable OIP aliases such as `oip-chat`, `oip-plan`, and `oip-agent`
- No complex multi-agent orchestration is required yet

### Suggested Tests

- Mode resolution tests
- Alias-to-mode mapping tests
- Routing behavior tests by mode
- Prompt construction tests

### Documentation Updates

- `docs/architecture.md`
- `docs/mvp.md`
- `docs/model-routing.md`

## Milestone 5: Admin Dashboard

### Objective

Introduce the administration surface of the platform.

### Deliverables

- Models management
- Providers management
- Memory management
- Knowledge management
- Tools management
- Monitoring entry points
- Settings management

### Acceptance Criteria

- OIP exposes a distinct administration surface
- Administrators can manage model aliases and provider settings
- Platform configuration is no longer hidden in code or environment variables alone
- The dashboard is positioned as a platform management feature, not an assistant feature

### Suggested Tests

- Admin UI smoke tests
- Model alias management tests
- Provider enable/disable tests
- Settings persistence tests

### Documentation Updates

- `docs/architecture.md`
- `docs/mvp.md`
- `docs/vision.md`

## Milestone 6: Memory Layer

### Objective

Implement the first working version of project memory as a platform capability.

### Deliverables

- Memory collections
- Memory entries
- Conversations
- Retention hooks
- Memory search and management

### Acceptance Criteria

- A workspace can create and store project memory
- Administrators can browse and manage memory
- Memory survives model changes and provider changes
- Retention and cleanup hooks are represented in the design

### Suggested Tests

- Memory persistence tests
- Search tests
- Retention behavior tests
- Admin management flow tests

### Documentation Updates

- `docs/memory-layer.md`
- `docs/domain-model.md`
- `docs/architecture.md`

## Milestone 7: Knowledge Layer

### Objective

Add managed knowledge ingestion and retrieval on top of the validated assistant runtime and memory foundation.

### Deliverables

- Documents
- Repositories
- Architecture docs
- Standards
- Policies
- Knowledge indexing

### Acceptance Criteria

- Administrators can manage knowledge sources
- OIP can index knowledge artifacts for later retrieval
- Knowledge is treated as a managed platform layer, not a side effect of prompts
- Source attribution remains preserved

### Suggested Tests

- Ingestion pipeline tests
- Source attribution tests
- Indexing tests
- Knowledge management smoke tests

### Documentation Updates

- `docs/knowledge-management.md`
- `docs/rag-architecture.md`
- `docs/architecture.md`

## Milestone 8: Delivery Wizard Integration

### Objective

Integrate Delivery Wizard as a product or tool layer on top of OIP foundations.

### Deliverables

- Delivery Wizard integration path
- OIP-backed assistant usage
- Shared memory and knowledge hooks
- Tool-level configuration boundaries

### Acceptance Criteria

- Delivery Wizard can consume OIP runtime capabilities without architectural redesign
- Delivery Wizard can rely on OIP-managed models, routing, and future memory
- Integration does not hard-code provider assumptions into Delivery Wizard

### Suggested Tests

- Integration smoke tests
- Shared runtime contract tests
- Cross-product configuration tests

### Documentation Updates

- `docs/vision.md`
- `docs/architecture.md`
- `docs/mcp-architecture.md`

## Milestone 9: Advanced Agent Framework

### Objective

Move from prompt- and routing-based modes into a fuller agent framework.

### Deliverables

- Advanced agent definitions
- Tool policies
- Approval workflows
- Managed prompts
- Deeper MCP usage

### Acceptance Criteria

- OIP supports richer agent behavior than mode-based prompt shaping alone
- Agents remain governed by the same platform controls
- Agent capabilities build on the validated assistant runtime rather than bypassing it

### Suggested Tests

- Agent workflow tests
- Tool policy tests
- Approval flow tests
- Observability and audit coverage tests

### Documentation Updates

- `docs/agent-framework.md`
- `docs/mcp-architecture.md`
- `docs/architecture.md`
- `docs/architecture.md`
