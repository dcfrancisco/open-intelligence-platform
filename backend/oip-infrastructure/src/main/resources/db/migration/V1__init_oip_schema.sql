CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE workspaces (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE memory_collections (
    id UUID PRIMARY KEY,
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE memory_sources (
    id UUID PRIMARY KEY,
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    collection_id UUID NOT NULL REFERENCES memory_collections(id) ON DELETE CASCADE,
    source_type VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    uri VARCHAR(1000),
    content_type VARCHAR(150),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE memory_entries (
    id UUID PRIMARY KEY,
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    collection_id UUID NOT NULL REFERENCES memory_collections(id) ON DELETE CASCADE,
    source_id UUID REFERENCES memory_sources(id) ON DELETE SET NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    embedding vector(1536),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE memory_tags (
    id UUID PRIMARY KEY,
    entry_id UUID NOT NULL REFERENCES memory_entries(id) ON DELETE CASCADE,
    value VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE conversations (
    id UUID PRIMARY KEY,
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE prompts (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE responses (
    id UUID PRIMARY KEY,
    prompt_id UUID NOT NULL REFERENCES prompts(id) ON DELETE CASCADE,
    provider_name VARCHAR(100) NOT NULL,
    model_name VARCHAR(150) NOT NULL,
    answer TEXT NOT NULL,
    source_references TEXT NOT NULL,
    retrieval_latency_ms BIGINT NOT NULL,
    generation_latency_ms BIGINT NOT NULL,
    total_latency_ms BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE providers (
    id UUID PRIMARY KEY,
    workspace_id UUID,
    name VARCHAR(150) NOT NULL,
    provider_type VARCHAR(50) NOT NULL,
    base_url VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE models (
    id UUID PRIMARY KEY,
    provider_id UUID NOT NULL REFERENCES providers(id) ON DELETE CASCADE,
    name VARCHAR(150) NOT NULL,
    version VARCHAR(50),
    context_window INTEGER,
    capabilities VARCHAR(500),
    cost_information VARCHAR(200),
    routing_priority INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE audit_events (
    id UUID PRIMARY KEY,
    workspace_id UUID,
    event_type VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id UUID,
    details TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_memory_collections_workspace_id ON memory_collections(workspace_id);
CREATE INDEX idx_memory_sources_workspace_id ON memory_sources(workspace_id);
CREATE INDEX idx_memory_sources_collection_id ON memory_sources(collection_id);
CREATE INDEX idx_memory_entries_workspace_id ON memory_entries(workspace_id);
CREATE INDEX idx_memory_entries_collection_id ON memory_entries(collection_id);
CREATE INDEX idx_memory_entries_source_id ON memory_entries(source_id);
CREATE INDEX idx_memory_tags_entry_id ON memory_tags(entry_id);
CREATE INDEX idx_conversations_workspace_id ON conversations(workspace_id);
CREATE INDEX idx_prompts_conversation_id ON prompts(conversation_id);
CREATE INDEX idx_responses_prompt_id ON responses(prompt_id);
CREATE INDEX idx_providers_status ON providers(status);
CREATE INDEX idx_models_provider_id ON models(provider_id);
CREATE INDEX idx_models_routing_priority ON models(routing_priority);
CREATE INDEX idx_audit_events_workspace_id ON audit_events(workspace_id);
CREATE INDEX idx_audit_events_entity ON audit_events(entity_type, entity_id);

CREATE INDEX idx_memory_entries_search ON memory_entries
USING GIN (to_tsvector('english', coalesce(title, '') || ' ' || coalesce(content, '')));
