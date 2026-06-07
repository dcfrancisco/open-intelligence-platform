# Security Policy

## Supported Scope

Open Intelligence Platform is currently in the architecture and MVP-planning stage. Security reports are still valuable because this repository will evolve into a runnable AI platform with document ingestion, model routing, retrieval, and provider integrations.

This policy applies to:

- Source code and workflows in this repository
- Documentation that could influence insecure deployment choices
- Future MVP components added under the documented repository structure

## Reporting a Vulnerability

Please do not report security vulnerabilities through public GitHub issues or pull requests.

Instead:

- Contact the maintainers privately through the repository security advisory feature if enabled
- If private security advisories are not available, open an issue requesting a private contact channel without disclosing exploit details

When reporting a vulnerability, include:

- A clear description of the issue
- Affected files, components, or documents
- Reproduction steps if applicable
- Potential impact
- Any suggested mitigation or fix

## What to Expect

Maintainers will aim to:

- Acknowledge receipt of the report
- Review the issue and assess impact
- Coordinate remediation before public disclosure when needed
- Credit responsible reporters when appropriate and desired

## Security Priorities for the MVP

The first runnable MVP will prioritize:

- Safe handling of provider credentials
- Secure document ingestion boundaries
- Protection of stored prompts, responses, and embeddings
- Basic authentication and session protection
- Input validation for file uploads and API requests
- Auditability of sensitive configuration changes

## Disclosure Guidance

Please avoid publishing proof-of-concept exploit details until maintainers have had a reasonable opportunity to investigate and respond.
