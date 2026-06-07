# ADR 0001: Platform Scope

- Status: Accepted
- Date: 2026-06-08

## Context

Open Intelligence Platform was conceived as a broad platform covering local and cloud LLMs, RAG, agentic workflows, continuous learning, fine-tuning, and enterprise knowledge management. That vision is intentionally ambitious, but the repository is at an early stage and needs a realistic starting point for open-source development.

Without a clear scope boundary, the project risks becoming a documentation-only concept or an over-scaffolded codebase with too many unfinished subsystems.

## Decision

The project will proceed in two layers:

- The architecture package remains the long-term target state for OIP.
- The first implementation milestone will be a narrowly scoped runnable MVP that proves the end-to-end knowledge and question-answering path.

The MVP will include:

- `Next.js` frontend
- `Spring Boot` modular monolith backend
- `PostgreSQL` with `pgvector`
- Local `Ollama` provider adapter
- One OpenAI-compatible provider adapter
- Basic model routing
- Document ingestion
- Chunking, embeddings, retrieval, and ask-question API
- Simple chat UI

The MVP will exclude:

- Large microservice decomposition
- Multi-agent orchestration
- Fine-tuning pipelines
- Advanced organizational intelligence workflows
- Enterprise-grade identity federation

## Consequences

### Positive

- Creates a realistic implementation target
- Makes contribution paths clear for open-source collaborators
- Proves the most important platform seams early: provider abstraction, retrieval, and UI-to-model flow

### Negative

- Some long-term architecture areas will remain documentation-only until later phases
- Contributors interested in advanced agent or training systems must wait for later milestones

## Rationale

OIP needs a working slice before it needs breadth. A disciplined scope boundary helps the project earn complexity instead of front-loading it.
