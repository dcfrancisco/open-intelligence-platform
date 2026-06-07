# Repository Structure

## Design Goals

The repository should separate user-facing application code, domain services, agent implementations, learning and training pipelines, deployment assets, and documentation. This keeps the platform understandable as it grows.

## Recommended Tree

```text
open-intelligence-platform/
├── README.md
├── frontend/
│   ├── apps/
│   │   └── web/
│   ├── packages/
│   │   ├── ui/
│   │   ├── auth-client/
│   │   └── api-client/
│   └── tests/
├── backend/
│   ├── gateway/
│   ├── auth-service/
│   ├── workspace-service/
│   ├── conversation-service/
│   ├── knowledge-service/
│   ├── learning-service/
│   ├── agent-orchestrator/
│   ├── model-router/
│   ├── training-service/
│   ├── provider-adapters/
│   │   ├── ollama-adapter/
│   │   ├── vllm-adapter/
│   │   ├── openai-adapter/
│   │   ├── anthropic-adapter/
│   │   ├── gemini-adapter/
│   │   ├── openrouter-adapter/
│   │   └── deepseek-adapter/
│   └── shared/
│       ├── domain/
│       ├── security/
│       ├── observability/
│       └── messaging/
├── agents/
│   ├── coding-agent/
│   ├── architecture-agent/
│   ├── documentation-agent/
│   ├── kt-agent/
│   ├── risk-agent/
│   └── operations-agent/
├── training/
│   ├── dataset-builder/
│   ├── evaluation/
│   ├── fine-tuning/
│   └── model-registry/
├── knowledge/
│   ├── connectors/
│   ├── ingestion/
│   ├── chunking/
│   ├── embeddings/
│   ├── retrieval/
│   └── schemas/
├── deployment/
│   ├── docker-compose/
│   ├── k8s/
│   ├── helm/
│   ├── monitoring/
│   └── scripts/
├── docs/
│   ├── architecture.md
│   ├── agent-framework.md
│   ├── deployment.md
│   ├── domain-model.md
│   ├── knowledge-management.md
│   ├── learning-pipeline.md
│   ├── model-routing.md
│   ├── observability.md
│   ├── openapi.md
│   ├── rag-architecture.md
│   ├── repository-structure.md
│   ├── roadmap.md
│   ├── security.md
│   ├── technology-stack.md
│   └── vision.md
└── .github/
    ├── workflows/
    └── ISSUE_TEMPLATE/
```

## Why This Structure

- `frontend/` isolates user experience concerns and reusable UI packages.
- `backend/` groups platform services while keeping provider adapters replaceable.
- `agents/` keeps specialized agent logic visible and independently evolvable.
- `training/` separates asynchronous ML operations from online request handling.
- `knowledge/` keeps ingestion and retrieval concerns modular.
- `deployment/` supports both local and enterprise operations.
- `docs/` keeps architecture and implementation intent close to the codebase.
