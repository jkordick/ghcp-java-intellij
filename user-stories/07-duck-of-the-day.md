# Story 7 — Duck of the Day

**ID:** `duck-of-the-day`
**Persona:** Quincy Quacker (customer)
**Priority:** Could
**Depends on:** `browse-catalog`

## Story

> **As** a customer with no specific need but plenty of free time,
> **I want** to see a single "Duck of the Day" featured on the home page,
> **so that** I have a reason to come back tomorrow.

## Acceptance criteria

- `GET /api/ducks/duck-of-the-day` returns one duck per calendar day.
- The same duck is returned for every request on the same day (deterministic), and a different duck on the next day.
- Sold-out ducks are skipped.
- If all ducks are sold out, the response is a friendly fallback message — never an error.
- The response includes the duck's detail endpoint URL for navigation.

## Out of scope

- Per-user personalization.
- Push notifications, email blasts.
- Manual override by the curator.
