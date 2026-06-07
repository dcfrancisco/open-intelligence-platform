# Open Intelligence Platform (OIP)

> Build your own private AI environment. Own your knowledge. Control your costs.

## Overview

Open Intelligence Platform (OIP) is a private AI development platform with memory. It is an open-source platform for building private, provider-neutral AI environments with a first-class organizational memory layer. It combines knowledge management, retrieval-augmented generation, agentic workflows, coding assistance, continuous learning, fine-tuning pipelines, and durable memory in one extensible architecture.

OIP is designed for developers, consultants, students, small businesses, delivery organizations, and enterprise teams that want AI capabilities without being locked into a single vendor or cloud.

The platform supports three deployment tiers:

- Developer or solo deployment
- Team or small business deployment
- Enterprise or production deployment

OIP is intentionally simple to start, but its architecture is designed to grow into enterprise-grade security, governance, operations, and integration requirements without redesigning the platform core.

The Memory Layer is the long-term knowledge system of OIP. Models may change over time, but organizational knowledge, engineering decisions, project history, and lessons learned remain preserved and continuously accessible.

## Current Status

The repository currently contains a production-oriented architecture package and open-source project foundation. It does not yet contain the full runnable platform implementation.

The next milestone is the first runnable MVP described in [docs/mvp.md](docs/mvp.md).

## Mission

OIP exists to make advanced AI systems practical, sovereign, and cost-governed. The platform gives teams a way to run local models when privacy or cost matters, use cloud models when capability or scale matters, and keep organizational knowledge under their own control.

OIP is not only an AI platform. It is a private AI development platform with organizational memory.

## Features

- Local and cloud model support through a unified routing layer
- First-class Memory Layer for preserving project, development, and organizational knowledge
- Retrieval-augmented generation for private knowledge bases
- Continuous knowledge learning from documents, interactions, and feedback
- Agent framework for coding, architecture, operations, documentation, and risk workflows
- Fine-tuning factory for dataset creation, training orchestration, and model lifecycle management
- Enterprise knowledge management with ownership, escalation, incident, runbook, and architecture decision capture
- Enterprise identity, policy, audit, governance, and cost-control foundations
- API-first platform design for UI, automation, and future product integrations
- Deployment flexibility across developer laptops, single servers, home labs, and Kubernetes clusters
- Security, observability, and governance designed in from the start

## Benefits

- Avoid vendor lock-in by abstracting model providers, vector stores, and deployment topologies
- Reduce cost by routing requests to the least expensive model that meets task requirements
- Improve privacy by running sensitive workloads locally
- Preserve institutional knowledge in a structured, searchable, continuously improving system
- Keep project history, engineering decisions, and lessons learned available even as models and providers change
- Support both individual productivity and enterprise-scale operations with the same platform model
- Give enterprise architects and platform teams a clear path from pilot deployment to production operations

## Architecture Summary

OIP uses a layered architecture:

- A `Next.js` web application provides the user experience for workspaces, knowledge, agents, and operations.
- A `Spring Boot` API layer exposes versioned APIs and enforces authentication, authorization, rate limits, quotas, and policy.
- Core services handle identity, workspaces, memory, knowledge ingestion and retrieval, model routing, provider and model registries, prompt governance, evaluation, cost governance, continuous learning, and training.
- Model providers are pluggable and support local runtimes such as `Ollama` and `vLLM`, as well as cloud providers such as `OpenAI`, `Anthropic`, `Google Gemini`, `OpenRouter`, and `DeepSeek`.
- Platform state is stored in `PostgreSQL`, vectors in `pgvector` or `ChromaDB`, and events in `Kafka`.
- Observability is implemented with `OpenTelemetry`, `Prometheus`, and `Grafana`, with audit and AI usage signals treated as first-class telemetry.

This architecture is intentionally extensible so future products such as Delivery Wizard, PortalOps AI, EventEase AI, and WorkTime AI can integrate through stable APIs, events, shared identity, memory services, and optional domain adapters without redesigning the platform core.

## Quick Start

The repository currently provides the architecture package, project governance files, and MVP implementation plan.

1. Clone the repository.
2. Read [docs/vision.md](docs/vision.md) to understand the target market and scope.
3. Review [docs/architecture.md](docs/architecture.md), [docs/security.md](docs/security.md), and [docs/deployment.md](docs/deployment.md) to align on production design.
4. Read [docs/mvp.md](docs/mvp.md) to understand the first runnable implementation target.
5. Use [docs/repository-structure.md](docs/repository-structure.md) and [docs/technology-stack.md](docs/technology-stack.md) to guide implementation.
6. Build incrementally following [docs/roadmap.md](docs/roadmap.md).

## Next Milestone

The next milestone is a runnable MVP with:

- `Spring Boot` modular monolith backend
- `Next.js` frontend
- `PostgreSQL` with `pgvector`
- `Ollama` adapter
- One OpenAI-compatible adapter
- Basic model router
- Document ingestion, chunking, embeddings, and retrieval
- Initial project memory collections and source attribution
- Ask-question API
- Simple chat UI

Scope and success criteria are documented in [docs/mvp.md](docs/mvp.md).

The MVP is intentionally small, but every MVP component is designed as the first version of a production-grade enterprise capability.

## Technology Stack

- Frontend: `React`, `Next.js`, `TypeScript`
- Backend: `Java 21`, `Spring Boot`, `Spring Security`, `Spring AI` patterns where appropriate
- AI Runtime: `Ollama`, `vLLM`
- Cloud AI Providers: `OpenAI`, `Anthropic`, `Google Gemini`, `OpenRouter`, `DeepSeek`
- Retrieval: `pgvector`, `ChromaDB`
- Database: `PostgreSQL`
- Messaging: `Kafka`
- Observability: `OpenTelemetry`, `Prometheus`, `Grafana`
- Deployment: `Docker`, `Docker Compose`, `Kubernetes`

Rationale is documented in [docs/technology-stack.md](docs/technology-stack.md).

## Roadmap

- Phase 0: Open Source Foundation
- Phase 1: Developer AI Workspace
- Phase 2: Team Knowledge Platform
- Phase 3: Enterprise AI Control Plane
- Phase 4: Agentic Delivery Platform
- Phase 5: Organizational Intelligence
- Phase 6: Continuous Learning Platform
- Phase 7: Fine-Tuning Factory
- Phase 8: Enterprise Production Operations

See [docs/roadmap.md](docs/roadmap.md) for objectives and deliverables.

## Contribution Guide

We want OIP to be both open-source friendly and production-minded. The best contribution target right now is the runnable MVP and the supporting architecture and governance docs around it.

1. Start with the architecture package and propose changes through pull requests.
2. Keep APIs versioned and backward compatible.
3. Preserve pluggability for models, vector stores, and deployment targets.
4. Document architecture decisions and tradeoffs in Markdown.
5. Add observability, security, and operational considerations as part of each feature, not as follow-up work.

Recommended contribution flow:

- Open an issue describing the problem, use case, and impact
- Link the impacted architecture areas
- Submit a focused pull request with docs and implementation changes together
- Include tests, operational notes, and migration notes where relevant

See [CONTRIBUTING.md](CONTRIBUTING.md) for the full contribution workflow, MVP guardrails, and pull request expectations.

## Out of Scope for MVP

The MVP intentionally does not include:

- Large microservice scaffolding
- Multi-agent orchestration
- Fine-tuning pipelines
- Kafka-based event-driven platform services
- Full enterprise SSO, ABAC, and production operations automation
- Full organizational intelligence workflows

These remain part of the broader architecture and roadmap, but not the first runnable implementation.

## Repository Documents

- [docs/vision.md](docs/vision.md)
- [docs/architecture.md](docs/architecture.md)
- [docs/domain-model.md](docs/domain-model.md)
- [docs/knowledge-management.md](docs/knowledge-management.md)
- [docs/model-routing.md](docs/model-routing.md)
- [docs/learning-pipeline.md](docs/learning-pipeline.md)
- [docs/memory-layer.md](docs/memory-layer.md)
- [docs/mvp.md](docs/mvp.md)
- [docs/rag-architecture.md](docs/rag-architecture.md)
- [docs/agent-framework.md](docs/agent-framework.md)
- [docs/security.md](docs/security.md)
- [docs/observability.md](docs/observability.md)
- [docs/deployment.md](docs/deployment.md)
- [docs/technology-stack.md](docs/technology-stack.md)
- [docs/roadmap.md](docs/roadmap.md)
- [docs/openapi.md](docs/openapi.md)
- [docs/repository-structure.md](docs/repository-structure.md)
- [docs/adr/](docs/adr/)
