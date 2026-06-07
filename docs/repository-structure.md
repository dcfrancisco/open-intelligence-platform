# Repository Structure

## Design Goals

The repository should support the first runnable MVP without premature service sprawl. The structure should make it easy to build a modular monolith now while preserving a clean path to future extraction if the platform grows.

## Recommended Tree

```text
open-intelligence-platform/
├── README.md
├── LICENSE
├── CONTRIBUTING.md
├── CODE_OF_CONDUCT.md
├── SECURITY.md
├── .editorconfig
├── .gitignore
├── frontend/
│   ├── apps/
│   │   └── web/
│   ├── packages/
│   │   ├── ui/
│   │   ├── auth-client/
│   │   └── api-client/
│   └── tests/
├── backend/
│   └── oip-server/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/oip/
│       │   │   │       ├── api/
│       │   │   │       ├── knowledge/
│       │   │   │       ├── memory/
│       │   │   │       ├── routing/
│       │   │   │       ├── providers/
│       │   │   │       ├── persistence/
│       │   │   │       └── shared/
│       │   │   └── resources/
│       │   │       └── db/migration/
│       │   └── test/
│       ├── build.gradle.kts
│       └── settings.gradle.kts
├── deployment/
│   ├── docker-compose/
│   ├── docker/
│   ├── scripts/
│   └── monitoring/
├── docs/
│   ├── adr/
│   │   ├── 0001-platform-scope.md
│   │   ├── 0002-local-and-cloud-model-routing.md
│   │   ├── 0003-rag-before-fine-tuning.md
│   │   └── 0004-spring-boot-and-nextjs.md
│   ├── architecture.md
│   ├── agent-framework.md
│   ├── deployment.md
│   ├── domain-model.md
│   ├── knowledge-management.md
│   ├── learning-pipeline.md
│   ├── implementation-principles.md
│   ├── mcp-architecture.md
│   ├── memory-layer.md
│   ├── build-plan.md
│   ├── mvp.md
│   ├── mvp-scope.md
│   ├── model-routing.md
│   ├── observability.md
│   ├── openapi.md
│   ├── rag-architecture.md
│   ├── repository-structure.md
│   ├── risk-register.md
│   ├── roadmap.md
│   ├── security.md
│   ├── technology-stack.md
│   └── vision.md
└── .github/
    ├── markdown-link-check-config.json
    ├── workflows/
    │   └── docs-check.yml
    └── ISSUE_TEMPLATE/
```

## Why This Structure

- `frontend/` isolates user experience concerns and reusable UI packages.
- `backend/oip-server/` keeps the MVP deployable as one application while maintaining clear internal module boundaries.
- `deployment/` supports both local and enterprise operations.
- `docs/` keeps architecture and implementation intent close to the codebase.
- `.github/` holds lightweight repository automation for documentation quality.

## Evolution Path

This structure is intentionally MVP-first. Future phases may introduce additional backend services, agent packages, training pipelines, and Kubernetes assets, but only after the modular monolith has proven the boundaries that deserve extraction.
