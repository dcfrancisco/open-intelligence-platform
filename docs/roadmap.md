# Roadmap

OIP grows in stages from open-source foundation to enterprise production operations. The platform stays simple to start, but each phase is designed to unlock the next level of governance, scale, and organizational value.

Memory is a cross-cutting platform capability across the entire roadmap. OIP is not only an AI platform. It is a private AI development platform with organizational memory.

Private First. Cloud Optional. Vendor Neutral.

Cloud providers are optional across all phases.

## Phase 0: Open Source Foundation

### Objectives

- Publish a GitHub-ready architecture package
- Establish project governance and contribution rules
- Create a stable foundation for open-source collaboration
- Preserve local deployment and vendor-neutral architecture from the start

### Deliverables

- GitHub-ready docs
- License
- Contribution guide
- Security policy
- Architecture decision records
- Project governance

## Phase 1: Developer AI Workspace

### Objectives

- Deliver the first runnable end-to-end product slice
- Prove local-first private AI with optional cloud augmentation
- Create the foundation for enterprise-grade capabilities later
- Keep full local and private operation viable without external AI providers

### Deliverables

- Local `Ollama`
- OpenAI-compatible provider
- Basic chat UI
- Basic RAG
- Document ingestion
- Project memory
- Source attribution
- Project collections
- `PostgreSQL` plus `pgvector`
- Docker Compose

Phase note: Phase 1 must remain usable with private models only. Cloud providers stay optional.

## Phase 2: Team Knowledge Platform

### Objectives

- Support shared usage by teams and small businesses
- Introduce collaborative knowledge boundaries and user management
- Add early operational visibility and shared configuration
- Preserve workspace-owned memory and vendor-neutral routing policy

### Deliverables

- Workspaces
- Users and roles
- Shared knowledge bases
- Source attribution
- Conversation history
- Provider configuration
- Usage tracking
- Development memory
- Reusable patterns
- Lessons learned
- Coding conventions
- Architecture guidance

## Phase 3: Enterprise AI Control Plane

### Objectives

- Add enterprise security, governance, and cost control
- Provide governed access to providers, models, and prompts
- Create policy-driven routing and auditability
- Preserve private deployment and local model operation as a first-class mode

### Deliverables

- SSO and OIDC
- RBAC and ABAC
- Provider registry
- Model registry
- Prompt registry
- Routing policies
- Audit logs
- Quotas and budgets

## Phase 4: Agentic Delivery Platform

### Objectives

- Introduce governed multi-agent execution for delivery workflows
- Add approval and tool-execution controls
- Support high-value engineering and delivery use cases
- Keep agent execution compatible with local models and model-independent MCP integration

### Deliverables

- Coding agent
- Architecture agent
- Documentation agent
- KT agent
- Risk agent
- Operations agent
- Approval workflows
- Tool execution controls

## Phase 4.5: MCP Integration Platform

### Objectives

- Introduce MCP as the standardized tool integration layer for agents and future products
- Govern internal and external MCP servers consistently
- Create reusable enterprise connectors without embedding tool logic into product code
- Keep MCP independent of the selected model or provider

### Deliverables

- MCP Gateway
- MCP Registry
- MCP Security
- MCP Governance
- MCP Monitoring
- Tool Marketplace
- Enterprise Connectors

## Phase 5: Organizational Intelligence

### Objectives

- Connect people, ownership, knowledge, and operational memory
- Convert project artifacts into reusable institutional intelligence
- Preserve private memory growth regardless of provider or assistant changes

### Deliverables

- SME directory
- Ownership maps
- Escalation paths
- Decision records
- Project memory
- Incident learning
- KT lifecycle
- Organizational memory

## Phase 6: Continuous Learning Platform

### Objectives

- Learn from interactions and feedback safely
- Build repeatable quality and regression mechanisms
- Separate knowledge learning from model training
- Keep learning pipelines usable in private-only deployments

### Deliverables

- Feedback capture
- Knowledge extraction
- Evaluation datasets
- Dataset builder
- Learning pipeline
- Quality scoring
- Regression testing for prompts and models
- Memory analytics
- Knowledge gaps
- Stale knowledge detection
- Duplicate knowledge detection
- Memory quality scoring

## Phase 7: Fine-Tuning Factory

### Objectives

- Operationalize domain adaptation with governed release flows
- Manage training datasets, model versions, and rollback safely
- Add release gates before production promotion
- Keep fine-tuning optional and preserve memory as the primary long-lived asset

### Deliverables

- Training dataset management
- LoRA fine-tuning
- Model versioning
- Model promotion workflow
- Rollback
- Safety evaluation
- Production release gates

## Phase 8: Enterprise Production Operations

### Objectives

- Make OIP production-ready for enterprise environments
- Formalize resilience, recovery, and operational hardening
- Support platform teams operating OIP at scale
- Preserve support for fully private, hybrid, and governed cloud operating modes

### Deliverables

- Kubernetes deployment
- HA architecture
- Backup and restore
- Disaster recovery
- Observability dashboards
- Runbooks
- Security hardening
- Compliance support

## Memory Layer Roadmap

The Memory Layer evolves as a reusable platform capability that can serve Delivery Wizard, PortalOps AI, EventEase AI, WorkTime AI, and future products without architectural redesign.

## Phase 1: Project Memory

### Capabilities

- Document ingestion
- Document retrieval
- Source attribution
- Project collections

## Phase 2: Development Memory

### Capabilities

- Reusable patterns
- Lessons learned
- Coding conventions
- Architecture guidance

## Phase 3: Organizational Memory

### Capabilities

- SME mapping
- Ownership mapping
- Escalation paths
- KT lifecycle

## Phase 4: Memory Analytics

### Capabilities

- Knowledge gaps
- Stale knowledge detection
- Duplicate knowledge detection
- Memory quality scoring
