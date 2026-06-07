# ADR 0004: Spring Boot and Next.js

- Status: Accepted
- Date: 2026-06-08

## Context

The architecture package recommends `Java 21`, `Spring Boot`, `React`, and `Next.js`, but the repository needs an explicit implementation decision for the first runnable milestone.

The project needs a backend foundation with strong structure, security, and operational maturity, and a frontend framework that supports a modern application shell and iterative product development.

## Decision

The first runnable MVP will use:

- `Spring Boot` as a modular monolith backend
- `Next.js` as the frontend application

The backend will organize capabilities into internal modules such as:

- API
- provider adapters
- routing
- knowledge ingestion
- retrieval
- persistence

The frontend will begin with a simple authenticated or local-development chat experience and document ingestion flow.

## Consequences

### Positive

- Aligns implementation with the documented target architecture
- Keeps backend complexity manageable without premature microservices
- Provides a strong path from MVP to a larger platform

### Negative

- Contributors expecting a JavaScript-only backend may face a higher barrier to entry
- The monolith will eventually need careful boundaries if extracted into services later

## Rationale

This combination balances developer productivity, operational maturity, and long-term maintainability better than splitting into many small services or introducing unnecessary framework diversity at the start.
