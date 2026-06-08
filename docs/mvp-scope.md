# MVP Scope

## Objective

Define the first runnable MVP clearly enough that contributors can build it without confusing target architecture with immediate implementation scope.

The MVP does not change OIP positioning. OIP remains a Private AI Development Platform with Memory, with private-first local model support and optional cloud integration.

## Included in MVP

- Local Docker Compose
- Spring Boot backend
- Next.js frontend
- PostgreSQL plus `pgvector`
- Ollama provider
- OpenAI-compatible provider
- Basic memory collection
- Document ingestion
- Ask-question API
- Chat UI

## MVP Boundaries

The MVP should prove one complete path:

1. Start the stack locally with Docker Compose
2. Upload project documents
3. Store them as project memory with source attribution
4. Ask a question through the UI
5. Retrieve relevant memory and knowledge context
6. Route the request to a configured provider
7. Return a grounded response with citations

## Explicitly Excluded from MVP

- Kubernetes
- Fine-tuning
- MCP integration platform
- Multi-agent execution
- Complex ABAC
- Enterprise SSO
- Distributed microservices
- Production HA

## Why These Are Excluded

These capabilities matter, but they are not required to prove the first runnable product slice. The architecture is designed to support them later through the existing identity, policy, memory, routing, and operations boundaries.

Cloud providers remain optional. The MVP must be usable with local and private models alone.

## MVP Success Criteria

- Local environment can be started by a contributor
- Documents can be ingested into project memory
- Ask-question flow uses stored memory and retrieval context
- Both local and OpenAI-compatible inference paths are usable
- Response includes source references
