# Contributing to Open Intelligence Platform

Thank you for contributing to Open Intelligence Platform (OIP). This repository is being built as an open-source, production-minded platform for private AI environments. We want contributions to be practical, well-explained, and durable.

## Before You Start

- Read [README.md](README.md) for project context and current status
- Review [docs/architecture.md](docs/architecture.md) and [docs/mvp.md](docs/mvp.md) before proposing implementation-heavy changes
- Check [docs/adr/](docs/adr/) for decisions that shape current scope
- Search existing issues and pull requests before opening a new one

## Ways to Contribute

- Improve documentation clarity, completeness, and accuracy
- Propose or implement MVP-aligned backend and frontend features
- Strengthen tests, local developer experience, or CI workflows
- Improve retrieval quality, provider adapters, or model routing within current scope
- Suggest architecture changes through an issue or ADR-oriented pull request

## Contribution Principles

- Keep the first runnable MVP intentionally small
- Prefer modular design over premature service splitting
- Preserve provider neutrality and local-first optionality
- Add operational thinking early: security, observability, and failure handling matter
- Document decisions when they affect architecture, APIs, storage, or runtime behavior

## Development Workflow

1. Open an issue for significant changes, especially architectural or cross-cutting work.
2. Align the proposal with the current MVP scope or explain why the scope should change.
3. Create a focused branch and keep the change narrowly scoped.
4. Update documentation together with code when behavior or design changes.
5. Submit a pull request with context, tradeoffs, and validation notes.

## Pull Request Expectations

Each pull request should include:

- A clear summary of the change
- The problem being solved
- Why the chosen approach fits the current architecture
- Testing or verification notes
- Documentation updates when relevant

For changes that affect architecture, also include:

- Impact on existing documents or ADRs
- Backward compatibility considerations
- Operational or security implications

## Architecture Changes

If you are changing a foundational design decision:

- Open an issue first unless the change is very small
- Update or add an ADR under [docs/adr/](docs/adr/)
- Update the affected architecture documents in `docs/`

## MVP Guardrails

The current milestone is the first runnable MVP described in [docs/mvp.md](docs/mvp.md). Contributions are especially welcome in these areas:

- Spring Boot modular monolith structure
- Next.js chat UI
- PostgreSQL plus `pgvector`
- Ollama adapter
- OpenAI-compatible adapter
- Model routing
- Document ingestion and retrieval
- Ask-question API

Out of scope for the MVP:

- Large microservice decomposition
- Multi-agent orchestration
- Fine-tuning pipelines
- Kafka-based event platform
- Enterprise SSO and advanced RBAC

## Coding and Documentation Standards

- Keep Markdown GitHub-ready and readable without special tooling
- Use Mermaid for architecture diagrams where diagrams add value
- Avoid vendor-specific assumptions in core abstractions
- Prefer small, reviewable changes over broad speculative scaffolding
- Do not add placeholder sections or unresolved marker text

## Reporting Security Issues

Do not open public issues for security vulnerabilities. Follow [SECURITY.md](SECURITY.md).

## License

By contributing to this repository, you agree that your contributions will be licensed under the Apache License 2.0 in [LICENSE](LICENSE).
