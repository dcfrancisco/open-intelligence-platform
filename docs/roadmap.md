# Roadmap

## Phase 1: AI Workspace

### Objectives

- Establish the core user workspace experience
- Enable chat, prompt management, and model access
- Support local and cloud providers behind a unified API

### Deliverables

- Web UI with authentication
- Workspace, conversation, and prompt APIs
- Initial model router with policy support
- Local `Ollama` integration
- Cloud provider adapters for at least one premium provider

## Phase 2: Knowledge Platform

### Objectives

- Build the canonical knowledge layer
- Enable ingestion, chunking, embeddings, and retrieval
- Support enterprise knowledge types beyond flat documents

### Deliverables

- Knowledge base service
- Document ingestion pipeline
- RAG retrieval path with citations
- Metadata model for incidents, runbooks, KT sessions, ADRs, SMEs, ownership, and escalations

## Phase 3: Agent Framework

### Objectives

- Introduce orchestrated multi-agent workflows
- Add tool use and governed execution
- Deliver role-based agents for high-value tasks

### Deliverables

- Agent orchestrator
- Coding, architecture, documentation, KT, risk, and operations agents
- Approval and audit controls for tool execution

## Phase 4: Organizational Intelligence

### Objectives

- Connect knowledge, people, and operational accountability
- Turn isolated project content into reusable organization-wide intelligence

### Deliverables

- SME and ownership mapping
- Escalation graph
- Cross-workspace knowledge discovery rules
- Integration contracts for Delivery Wizard, PortalOps AI, EventEase, and WorkTime

## Phase 5: Continuous Learning

### Objectives

- Learn from interactions, feedback, and operations
- Improve retrieval quality continuously
- Build trusted curation pipelines for reusable insight

### Deliverables

- Interaction capture
- Feedback and correction workflows
- Knowledge extraction pipeline
- Versioned learning datasets

## Phase 6: Fine-Tuning Factory

### Objectives

- Turn curated data into specialized model improvements
- Operationalize training, evaluation, and model promotion

### Deliverables

- Dataset builder
- Fine-tune orchestration
- Evaluation and registry flows
- Policy-based release of fine-tuned models into the router
