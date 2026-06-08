# Developer Integrations

## Purpose

OIP is not positioned as a replacement for developer assistants such as `GitHub Copilot`, `Cursor`, `Claude Code`, `Codex`, `JetBrains AI Assistant`, or `Continue.dev`.

OIP provides the platform services those tools can use:

- Memory Layer
- Knowledge Layer
- MCP Layer
- Routing Layer
- Governance Layer

Private First. Cloud Optional. Vendor Neutral.

## Positioning

Developer assistants focus on interaction at the editor or task surface. OIP focuses on long-lived knowledge, governed model routing, reusable tool integration, and enterprise controls behind that interaction surface.

That separation matters because assistants may change over time. Memory remains. Routing policy remains. MCP integrations remain. Governance remains.

## Integration Model

```mermaid
flowchart TD
    Developer[Developer] --> Assistant[Copilot, Cursor, Codex, Claude Code, Continue.dev]
    Assistant --> OIP[OIP]
    OIP --> Memory[Memory Layer]
    OIP --> Knowledge[Knowledge Layer]
    OIP --> MCP[MCP Layer]
    OIP --> Routing[Routing Layer]
    OIP --> Governance[Governance Layer]
    Routing --> Local[Local Models]
    Routing --> Cloud[Optional Cloud Models]
```

## Example Flows

### Coding

```mermaid
flowchart TD
    Developer[Developer] --> Cursor[Cursor or Copilot]
    Cursor --> OIP[OIP]
    OIP --> ProjectMemory[Project Memory]
    OIP --> DevMemory[Development Memory]
    OIP --> MCP[MCP Tools]
    OIP --> LocalCoder[Qwen Coder Local]
```

### Architecture

```mermaid
flowchart TD
    Developer[Developer] --> Codex[Codex or Claude Code]
    Codex --> OIP[OIP]
    OIP --> ADRs[ADRs and Architecture Memory]
    OIP --> Standards[Development Standards]
    OIP --> Router[Model Router]
    Router --> LocalArch[Local Model]
    Router --> CloudFallback[Optional Cloud Fallback]
```

## Why OIP Sits Behind Developer Tools

- Memory survives tool changes
- Routing policy survives provider changes
- MCP integration survives model changes
- Governance survives workflow changes
- Knowledge remains under organizational ownership

## Ownership Model

Users and organizations retain ownership of:

- Knowledge
- Memory
- Documents
- Models
- Agents
- MCP integrations
- Workflows

Developer tools are consumers of OIP capabilities, not owners of those assets.
