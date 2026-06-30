# Story 2 — View a duck's detail page

**ID:** `duck-detail`
**Persona:** Quincy Quacker (customer)
**Priority:** MVP
**Depends on:** `browse-catalog`

## Story

> **As** a curious customer,
> **I want** to view the full details of a specific duck,
> **so that** I can read its backstory, personality traits and special powers before I buy it.

## Acceptance criteria

- `GET /api/ducks/{id}` returns the full duck record: name, category, price, tagline, long description, personality traits, and stock level.
- An invalid or unknown ID returns HTTP 404 with a clear error message.
- The catalog listing includes the duck ID so clients can navigate to the detail endpoint.

## Out of scope

- Customer reviews.
- "Related ducks" suggestions.
