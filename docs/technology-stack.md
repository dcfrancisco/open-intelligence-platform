# Technology Stack

## Frontend

### React

`React` is recommended because OIP needs a highly interactive application for chat, workspaces, admin consoles, and agent workflows. React's ecosystem and composability make it suitable for long-term product growth.

### Next.js

`Next.js` is recommended because it supports authenticated application flows, server-side rendering, route-based code organization, and strong developer productivity. It is a practical choice for a platform UI rather than a static site.

## Backend

### Java 21

`Java 21` provides long-term support, modern language features, strong concurrency foundations, and mature operational characteristics. For an enterprise-capable AI platform, these qualities matter more than short-term startup convenience.

### Spring Boot

`Spring Boot` is recommended because it offers mature patterns for API design, configuration, dependency injection, security, observability, and service modularity. It also aligns well with organizations that want maintainable, production-grade backend services.

## AI

### Ollama

`Ollama` is a strong local inference choice for developers and lightweight self-hosted environments. It is easy to set up and supports practical local model experimentation.

### vLLM

`vLLM` is recommended for high-throughput inference, GPU utilization efficiency, and more scalable self-hosted model serving. It is a strong fit for enterprise or team-level local model infrastructure.

## Vector Layer

### pgvector

`pgvector` is ideal when operational simplicity matters. Teams can keep relational and vector data close together in PostgreSQL, reducing infrastructure sprawl for early phases.

### ChromaDB

`ChromaDB` is useful when teams want a dedicated vector-focused service or prefer its operational model for experimentation and retrieval workloads.

## Database

### PostgreSQL

`PostgreSQL` should be the primary transactional database because it is reliable, open-source, extensible, and well understood. OIP can use it for platform metadata, knowledge records, workflow state, configuration, and even vector storage when using `pgvector`.

## Messaging

### Kafka

`Kafka` is recommended for asynchronous event flows such as ingestion, learning, dataset generation, training orchestration, audit propagation, and cross-product integration. It supports durable decoupling between services and products.

## Observability

### OpenTelemetry

`OpenTelemetry` provides vendor-neutral instrumentation that aligns directly with OIP's anti-lock-in philosophy.

### Prometheus

`Prometheus` is a strong fit for metrics collection, alerting inputs, and operational visibility in both single-node and Kubernetes deployments.

### Grafana

`Grafana` gives unified dashboards for metrics, logs, and traces and is widely adopted by both small and large operations teams.

## Containers and Orchestration

### Docker

`Docker` is the standard packaging layer for local development, CI, and server deployment.

### Kubernetes

`Kubernetes` is the recommended enterprise runtime because it supports scaling, scheduling, isolation, rollout control, and operational consistency for distributed AI workloads.

## Why This Stack

- It is open-source friendly and production-proven.
- It supports both small and large deployments.
- It minimizes forced vendor dependencies.
- It matches the platform's need for modularity, observability, and long-term maintainability.
