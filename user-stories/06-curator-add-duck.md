# Story 6 — Curator adds a new duck

**ID:** `curator-add-duck`
**Persona:** Dr. Mallard (Duck Curator / admin)
**Priority:** Should

## Story

> **As** Dr. Mallard, sole arbiter of duck quality,
> **I want** to add a new duck to the catalog via an admin endpoint,
> **so that** I can expand the flock without editing seed files by hand.

## Acceptance criteria

- `POST /api/admin/ducks` accepts a new duck (name, category, price, tagline, description, personality traits, initial stock).
- The endpoint requires a shared admin password supplied via the `ADMIN_PASSWORD` environment variable (HTTP Basic or a custom header). Requests without (or with the wrong) password are rejected with HTTP 401.
- Server-side validation rejects: duplicate names, negative prices, negative stock, missing required fields. Errors are returned with clear messages.
- A successfully added duck appears immediately in the catalog listing.
- The action is logged (SLF4J to stdout) with timestamp and duck name. No customer PII is ever logged.

## Out of scope

- A full admin UI. A documented `curl`/REST endpoint is sufficient.
- Editing or deleting existing ducks.
- Per-user admin accounts. One shared password is fine for this workshop.
