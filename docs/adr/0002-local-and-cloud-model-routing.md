# ADR 0002: Local and Cloud Model Routing

- Status: Accepted
- Date: 2026-06-08

## Context

One of OIP's core promises is provider neutrality. Users need the ability to run workloads privately on local infrastructure when cost, privacy, or offline availability matters, and to use cloud models when stronger capability is required.

If provider handling is embedded directly in UI code or feature-specific services, the system will quickly become difficult to govern and extend.

## Decision

OIP will implement a dedicated model routing abstraction from the beginning, even in the modular monolith MVP.

The MVP router will:

- Accept a normalized inference request
- Evaluate user or workspace preferences
- Support local `Ollama` and one OpenAI-compatible cloud provider integration
- Prefer local models by policy when appropriate
- Fall back to cloud when allowed and when capability or availability requires it

The initial routing logic will remain intentionally simple, but the abstraction boundary will be designed to expand later to richer policies for cost, latency, and provider health.

## Consequences

### Positive

- Preserves the anti-lock-in value proposition from day one
- Avoids rewriting model invocation logic when new providers are added
- Creates a reusable contract for future applications that integrate with OIP

### Negative

- Adds some early abstraction cost compared with calling one provider directly
- Requires careful interface design to avoid leaking provider-specific concepts

## Rationale

Routing is not an advanced feature added later. It is the mechanism that makes OIP distinct from a single-provider application.
