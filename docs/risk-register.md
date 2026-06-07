# Risk Register

## Local Hardware Limitations

Impact:
Local inference may be slow, memory-constrained, or unavailable on contributor machines.

Mitigation:
Keep local runtime profiles lightweight, support smaller local models, and allow fallback to the OpenAI-compatible provider.

## Model Quality

Impact:
Responses may be weak, incomplete, or inconsistent across providers and model sizes.

Mitigation:
Use retrieval and memory grounding, compare providers through evaluation datasets, and keep provider routing configurable.

## Provider API Changes

Impact:
External provider changes can break integrations or alter behavior unexpectedly.

Mitigation:
Use adapter abstractions, contract tests, version-pinned client libraries where practical, and provider health checks.

## Privacy Leakage

Impact:
Sensitive data could be exposed through prompts, logs, provider calls, or exports.

Mitigation:
Apply workspace isolation, secrets handling, policy enforcement, logging redaction, and provider restrictions for sensitive workloads.

## Hallucination

Impact:
Users may receive unsupported or incorrect answers.

Mitigation:
Use source attribution, retrieval grounding, response review options, and clear boundaries between retrieved evidence and generated response text.

## Poor Retrieval Quality

Impact:
Answers may be irrelevant because the right context is not found.

Mitigation:
Invest early in chunking strategy, metadata filtering, memory structure, reranking, and retrieval-focused tests.

## Embedding Drift

Impact:
Changes in embedding models or indexing logic may reduce retrieval quality over time.

Mitigation:
Store embedding metadata, support re-indexing, track retrieval quality metrics, and validate changes against sample datasets.

## Cost Overruns

Impact:
Cloud usage can become unexpectedly expensive.

Mitigation:
Track tokens and provider cost, enforce quotas, prefer local inference where appropriate, and use routing policies with fallback controls.

## Over-Engineering

Impact:
The project may become architecture-heavy before a working product exists.

Mitigation:
Build the modular monolith first, sequence work through milestone-based delivery, and keep MVP scope explicit.

## Scope Creep

Impact:
Too many adjacent features can delay the first runnable release.

Mitigation:
Use `docs/mvp-scope.md` and `docs/build-plan.md` as control documents and evaluate new work against milestone acceptance criteria.

## Enterprise Complexity Too Early

Impact:
Trying to implement full enterprise behavior too soon can block contributor momentum.

Mitigation:
Preserve enterprise-ready boundaries in the architecture while implementing only the minimum viable version of each capability in the MVP.
