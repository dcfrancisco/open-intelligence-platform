# Open Intelligence Platform (OIP)

> Build your own private AI environment. Own your knowledge. Control your costs.

## Overview

Open Intelligence Platform (OIP) is an open-source platform for building private, provider-neutral AI environments. It combines knowledge management, retrieval-augmented generation, agentic workflows, coding assistance, continuous learning, and fine-tuning pipelines in one extensible architecture.

OIP is designed for developers, consultants, students, small businesses, delivery organizations, and enterprise teams that want AI capabilities without being locked into a single vendor or cloud.

## Mission

OIP exists to make advanced AI systems practical, sovereign, and cost-governed. The platform gives teams a way to run local models when privacy or cost matters, use cloud models when capability or scale matters, and keep organizational knowledge under their own control.

## Features

- Local and cloud model support through a unified routing layer
- Retrieval-augmented generation for private knowledge bases
- Continuous knowledge learning from documents, interactions, and feedback
- Agent framework for coding, architecture, operations, documentation, and risk workflows
- Fine-tuning factory for dataset creation, training orchestration, and model lifecycle management
- Enterprise knowledge management with ownership, escalation, incident, runbook, and architecture decision capture
- API-first platform design for UI, automation, and future product integrations
- Deployment flexibility across developer laptops, single servers, home labs, and Kubernetes clusters
- Security, observability, and governance designed in from the start

## Benefits

- Avoid vendor lock-in by abstracting model providers, vector stores, and deployment topologies
- Reduce cost by routing requests to the least expensive model that meets task requirements
- Improve privacy by running sensitive workloads locally
- Preserve institutional knowledge in a structured, searchable, continuously improving system
- Support both individual productivity and enterprise-scale operations with the same platform model

## Architecture Summary

OIP uses a layered architecture:

- A `Next.js` web application provides the user experience for workspaces, knowledge, agents, and operations.
- A `Spring Boot` API layer exposes versioned APIs and enforces authentication, authorization, rate limits, and policy.
- Core services handle agent orchestration, knowledge ingestion and retrieval, model routing, continuous learning, and training.
- Model providers are pluggable and support local runtimes such as `Ollama` and `vLLM`, as well as cloud providers such as `OpenAI`, `Anthropic`, `Google Gemini`, `OpenRouter`, and `DeepSeek`.
- Platform state is stored in `PostgreSQL`, vectors in `pgvector` or `ChromaDB`, and events in `Kafka`.
- Observability is implemented with `OpenTelemetry`, `Prometheus`, and `Grafana`.

This architecture is intentionally extensible so future products such as Delivery Wizard, PortalOps AI, EventEase, and WorkTime can integrate through stable APIs, events, shared identity, and optional domain adapters without redesigning the platform core.

## Quick Start

The repository currently provides the production architecture package and implementation blueprint.

1. Clone the repository.
2. Read [docs/vision.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/vision.md) to understand the target market and scope.
3. Review [docs/architecture.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/architecture.md), [docs/security.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/security.md), and [docs/deployment.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/deployment.md) to align on production design.
4. Use [docs/repository-structure.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/repository-structure.md) and [docs/technology-stack.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/technology-stack.md) to scaffold the implementation.
5. Build incrementally following [docs/roadmap.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/roadmap.md).

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

Rationale is documented in [docs/technology-stack.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/technology-stack.md).

## Roadmap

- Phase 1: AI Workspace
- Phase 2: Knowledge Platform
- Phase 3: Agent Framework
- Phase 4: Organizational Intelligence
- Phase 5: Continuous Learning
- Phase 6: Fine-Tuning Factory

See [docs/roadmap.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/roadmap.md) for objectives and deliverables.

## Contribution Guide

We want OIP to be both open-source friendly and production-minded.

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

## Repository Documents

- [docs/vision.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/vision.md)
- [docs/architecture.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/architecture.md)
- [docs/domain-model.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/domain-model.md)
- [docs/knowledge-management.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/knowledge-management.md)
- [docs/model-routing.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/model-routing.md)
- [docs/learning-pipeline.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/learning-pipeline.md)
- [docs/rag-architecture.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/rag-architecture.md)
- [docs/agent-framework.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/agent-framework.md)
- [docs/security.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/security.md)
- [docs/observability.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/observability.md)
- [docs/deployment.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/deployment.md)
- [docs/technology-stack.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/technology-stack.md)
- [docs/roadmap.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/roadmap.md)
- [docs/openapi.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/openapi.md)
- [docs/repository-structure.md](/Users/dannyfrancisco/Documents/Open%20Intelligence%20Platform/docs/repository-structure.md)
