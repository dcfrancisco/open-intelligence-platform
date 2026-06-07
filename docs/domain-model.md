# Domain Model

## Overview

The OIP domain model separates platform identity, memory assets, knowledge assets, AI interactions, model infrastructure, and governance records. This structure keeps user-facing workflows clear while allowing the platform to evolve from a single-user environment into a multi-team system.

## Entity Relationship Diagram

```mermaid
erDiagram
    User ||--o{ Workspace : belongs_to
    Workspace ||--o{ KnowledgeBase : contains
    Workspace ||--o{ MemoryCollection : owns
    KnowledgeBase ||--o{ Document : stores
    Document ||--o{ Chunk : split_into
    Chunk ||--|| Embedding : represented_by
    MemoryCollection ||--o{ Memory : groups
    Memory ||--o{ MemoryEntry : contains
    MemoryEntry }o--|| MemorySource : originated_from
    MemoryEntry ||--o{ MemoryRelationship : links
    MemoryEntry ||--o{ MemoryTag : labeled_by
    MemoryEntry ||--o{ MemoryFeedback : reviewed_by
    MemoryEntry ||--o{ MemorySnapshot : versioned_as
    Workspace ||--o{ McpServer : allows
    McpServer ||--o{ McpTool : exposes
    McpServer ||--o{ McpResource : shares
    McpServer ||--o{ McpPrompt : provides
    McpServer ||--o{ McpConnection : connects_through
    McpServer ||--o{ McpCredential : secured_by
    McpServer ||--o{ McpPolicy : governed_by
    McpServer ||--o{ McpAuditEvent : audited_by
    Workspace ||--o{ Conversation : has
    Conversation ||--o{ Prompt : includes
    Prompt ||--o{ Response : receives
    Workspace ||--o{ Agent : enables
    Agent ||--o{ Workflow : executes
    Provider ||--o{ Model : offers
    Workspace ||--o{ Provider : configures
    Workspace ||--o{ TrainingDataset : generates
    TrainingDataset ||--o{ FineTuneJob : used_by
    Model ||--o{ FineTuneJob : target_model
    Conversation ||--o{ Feedback : collects
    Response ||--o{ Feedback : evaluated_by
    User ||--o{ MemoryFeedback : provides
    User ||--o{ AuditEvent : creates
    Workspace ||--o{ AuditEvent : records

    User {
        uuid id
        string email
        string displayName
        string role
    }
    Workspace {
        uuid id
        string name
        string type
        string policyProfile
    }
    KnowledgeBase {
        uuid id
        string name
        string scope
        string retentionPolicy
    }
    MemoryCollection {
        uuid id
        string name
        string memoryLevel
        string isolationMode
    }
    Memory {
        uuid id
        string title
        string memoryType
        string classification
    }
    MemoryEntry {
        uuid id
        string entryType
        text content
        datetime effectiveAt
    }
    MemoryRelationship {
        uuid id
        string relationshipType
        string targetRef
        datetime createdAt
    }
    MemoryTag {
        uuid id
        string tagName
        string tagType
    }
    MemoryFeedback {
        uuid id
        int rating
        string disposition
        text notes
    }
    MemorySource {
        uuid id
        string sourceType
        string sourceRef
        string owner
    }
    MemorySnapshot {
        uuid id
        string snapshotVersion
        datetime capturedAt
        string checksum
    }
    McpServer {
        uuid id
        string name
        string version
        string endpoint
        string status
    }
    McpTool {
        uuid id
        string toolName
        string capability
        string certificationState
    }
    McpResource {
        uuid id
        string resourceName
        string resourceType
        string visibility
    }
    McpPrompt {
        uuid id
        string promptName
        string version
        string status
    }
    McpPolicy {
        uuid id
        string policyName
        string effect
        string scope
    }
    McpConnection {
        uuid id
        string connectionType
        string authenticationType
        string state
    }
    McpAuditEvent {
        uuid id
        string action
        string result
        datetime occurredAt
    }
    McpCredential {
        uuid id
        string credentialType
        string secretRef
        string rotationState
    }
    Document {
        uuid id
        string title
        string documentType
        string sourceSystem
        string owner
    }
    Chunk {
        uuid id
        int chunkIndex
        text content
        string checksum
    }
    Embedding {
        uuid id
        string modelName
        string vectorStore
        datetime createdAt
    }
    Conversation {
        uuid id
        string channel
        string status
        datetime startedAt
    }
    Prompt {
        uuid id
        text content
        string intent
        datetime createdAt
    }
    Response {
        uuid id
        text content
        string modelUsed
        decimal estimatedCost
    }
    Agent {
        uuid id
        string name
        string agentType
        string policyMode
    }
    Workflow {
        uuid id
        string name
        string state
        string outcome
    }
    Provider {
        uuid id
        string name
        string providerType
        string endpoint
    }
    Model {
        uuid id
        string name
        string capabilityClass
        string hostingMode
    }
    TrainingDataset {
        uuid id
        string name
        string datasetType
        string approvalStatus
    }
    FineTuneJob {
        uuid id
        string status
        string trainingTarget
        datetime startedAt
    }
    Feedback {
        uuid id
        int rating
        text comments
        string disposition
    }
    AuditEvent {
        uuid id
        string action
        string entityType
        datetime occurredAt
    }
```

## Relationship Explanation

### User and Workspace

A user can belong to multiple workspaces because consultants, delivery leads, and enterprise staff often operate across teams or clients. A workspace is the primary boundary for policies, providers, knowledge scope, and auditability.

### Workspace and KnowledgeBase

A workspace can contain one or more knowledge bases so teams can separate product, client, operations, or regulatory content without duplicating platform infrastructure.

### Workspace and MemoryCollection

A workspace can contain multiple memory collections because project memory, development memory, and organizational memory often have different sharing and governance rules. Memory collections allow OIP to preserve durable knowledge without forcing every memory artifact into one global pool.

### MemoryCollection, Memory, and MemoryEntry

A memory collection groups related memory assets. Each memory record represents a long-lived topic such as a project decision, engineering pattern, operational lesson, or escalation path. Memory entries capture the evolving facts, updates, and supporting details within that memory.

### MemorySource, MemoryRelationship, MemoryTag, MemoryFeedback, and MemorySnapshot

Memory sources preserve provenance to documents, conversations, repositories, incidents, ADRs, and reviews. Memory relationships link related memories together. Tags support classification and discovery. Feedback helps improve memory quality. Snapshots preserve historical states so memory can evolve without losing traceability.

### MCP Platform Entities

MCP servers represent managed tool endpoints. Tools, resources, and prompts describe the capabilities exposed by those servers. MCP connections and credentials define how OIP connects to them. MCP policies govern access. MCP audit events preserve operational and governance history for tool usage and server lifecycle actions.

### KnowledgeBase, Document, Chunk, and Embedding

Documents are the authoritative units of ingested content. Each document is chunked for retrieval, and each chunk is represented by an embedding. Keeping chunk and embedding as explicit entities preserves lineage, re-indexing flexibility, and auditability.

### Conversation, Prompt, Response, and Feedback

Conversations capture a sequence of prompts and responses. Feedback is attached to responses and conversations so OIP can distinguish between operational satisfaction, factual correction, and reusable learning signals.

### Agent and Workflow

An agent is a reusable capability definition. A workflow is a concrete execution of that capability in a given context. This lets OIP track both what an agent is and what it did.

### Provider and Model

Providers expose one or more models. This matters because routing policy is typically applied at both levels: provider health and cost can change independently of individual model capability.

### TrainingDataset and FineTuneJob

Training datasets are approved, versioned artifacts derived from knowledge or interaction data. Fine-tune jobs consume those datasets and produce updated or new models.

### AuditEvent

Audit events record sensitive or important actions such as document ingestion, provider changes, agent execution, model invocations, feedback approvals, and administrative changes.
