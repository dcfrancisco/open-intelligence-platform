# ADR 0003: RAG Before Fine-Tuning

- Status: Accepted
- Date: 2026-06-08

## Context

OIP's long-term vision includes both knowledge learning and model training. However, early-stage AI platforms often over-invest in training workflows before they have established strong retrieval, document quality, or feedback loops.

For most factual and organizational use cases, retrieval quality improves outcomes faster and with lower operational risk than fine-tuning.

## Decision

OIP will prioritize retrieval-augmented generation before implementing fine-tuning infrastructure.

The MVP will focus on:

- Reliable document ingestion
- Sensible chunking
- Embedding generation
- Vector retrieval
- Context assembly
- Ask-question flows over ingested knowledge

Fine-tuning will remain part of the target architecture and roadmap, but not part of the first runnable implementation.

## Consequences

### Positive

- Delivers useful results earlier
- Reduces infrastructure and governance complexity
- Encourages better source data and metadata discipline

### Negative

- Some use cases that benefit from behavior tuning will wait for later phases
- Contributors focused on training systems will not see immediate implementation work in the MVP

## Rationale

RAG is the fastest path to a working private intelligence platform. Fine-tuning becomes more valuable after the platform can already capture and curate reliable knowledge.
