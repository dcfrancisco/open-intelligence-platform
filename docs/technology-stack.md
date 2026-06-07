# Technology Stack

OIP uses an enterprise-capable, open-source-friendly stack that works across three deployment tiers:

- Developer or solo deployment
- Team or small business deployment
- Enterprise or production deployment

The goal is not to optimize only for local experimentation. The stack must let a contributor start quickly while giving platform teams a credible path to secure, governed, production operations.

## Frontend

### React

`React` is recommended because OIP needs a highly interactive application for chat, workspaces, admin consoles, and agent workflows. React's ecosystem and composability make it suitable for long-term product growth.

### Next.js

`Next.js` is recommended because it supports authenticated application flows, server-side rendering, route-based code organization, and strong developer productivity. It is a practical choice for a platform UI rather than a static site and scales well from a small chat interface to an enterprise administration and operations console.

## Backend

### Java 21

`Java 21` provides long-term support, modern language features, strong concurrency foundations, and mature operational characteristics. For an enterprise-capable AI platform, these qualities matter more than short-term startup convenience.

### Spring Boot

`Spring Boot` is recommended because it offers mature patterns for API design, configuration, dependency injection, security, observability, and service modularity. It also aligns well with organizations that want maintainable, production-grade backend services, environment promotion, and long-lived platform governance.

## AI

### Ollama

`Ollama` is a strong local inference choice for developers and lightweight self-hosted environments. It is easy to set up and supports practical local model experimentation, private knowledge use, and developer-first adoption.

### vLLM

`vLLM` is recommended for high-throughput inference, GPU utilization efficiency, and more scalable self-hosted model serving. It is a strong fit for enterprise or team-level local model infrastructure.

## Vector Layer

### pgvector

`pgvector` is ideal when operational simplicity matters. Teams can keep relational and vector data close together in PostgreSQL, reducing infrastructure sprawl for early phases.

### ChromaDB

`ChromaDB` is useful when teams want a dedicated vector-focused service or prefer its operational model for experimentation and retrieval workloads.

## Database

### PostgreSQL

`PostgreSQL` should be the primary transactional database because it is reliable, open-source, extensible, and well understood. OIP can use it for platform metadata, knowledge records, workflow state, configuration, audit records, registries, and even vector storage when using `pgvector`.

## Messaging

### Kafka

`Kafka` is recommended for asynchronous event flows such as ingestion, learning, dataset generation, training orchestration, audit propagation, policy events, and cross-product integration. It supports durable decoupling between services and products.

## Security and Identity

### OIDC and SAML-Compatible Identity Providers

Enterprise identity providers are recommended for SSO, lifecycle management, and centralized access policy. OIP should integrate cleanly with OIDC providers and support SAML federation patterns where required.

### LDAP and Active Directory

LDAP or Active Directory integration is important for organizations that still use directory-backed group and user management. This helps OIP fit existing enterprise identity landscapes instead of forcing greenfield assumptions.

### Secrets Management

Use local environment-backed secrets for developer installs and enterprise secret managers for production. This split keeps local setup simple while matching enterprise expectations for rotation, auditability, and scoped access.

## Observability

### OpenTelemetry

`OpenTelemetry` provides vendor-neutral instrumentation that aligns directly with OIP's anti-lock-in philosophy.

### Prometheus

`Prometheus` is a strong fit for metrics collection, alerting inputs, and operational visibility in both single-node and Kubernetes deployments.

### Grafana

`Grafana` gives unified dashboards for metrics, logs, and traces and is widely adopted by both small and large operations teams.

## Enterprise Integrations

OIP should be designed to integrate with:

- GitHub, GitLab, and Bitbucket
- Jira and Azure DevOps
- Confluence and SharePoint
- Slack and Teams
- ServiceNow
- Kubernetes
- OpenTelemetry-based observability backends

These integrations are strategic because enterprise value comes from connecting AI to real delivery, knowledge, and operational systems.

## Containers and Orchestration

### Docker

`Docker` is the standard packaging layer for local development, CI, and server deployment.

### Kubernetes

`Kubernetes` is the recommended enterprise runtime because it supports scaling, scheduling, isolation, rollout control, blue-green or rolling deployment patterns, and operational consistency for distributed AI workloads.

## Why This Stack

- It is open-source friendly and production-proven.
- It supports both small and large deployments.
- It minimizes forced vendor dependencies.
- It matches the platform's need for modularity, observability, governance, and long-term maintainability.
