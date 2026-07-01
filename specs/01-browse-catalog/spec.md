# Spec — Browse the Duck Catalog

**Story:** `01-browse-catalog`
**Status:** Draft

---

## Problem

Visitors to The Rubber Duck Emporium need a way to see all available ducks at a glance so they can decide whether to explore further. Without a catalog endpoint, the store has no storefront.

## Users

| Persona | Role |
|---------|------|
| Quincy Quacker | Unauthenticated visitor browsing the catalog |

## Scope

### In scope

- REST endpoint `GET /api/ducks` returning the full catalog as JSON.
- `Duck` entity with fields: id, name, price (cents), tagline, and a FK to `Category`.
- `Category` entity with fields: id, name.
- Seed data initializer populating ≥ 10 ducks across ≥ 3 categories on application startup.
- Empty-catalog case returns `200 OK` with an empty JSON array.
- Automated tests (controller + repository layer).

### Out of scope

- Pagination.
- Images / binary assets.
- Sorting or filtering controls (covered in story 5).
- Authentication / authorization.

## Functional Requirements

| ID | Requirement |
|----|-------------|
| FR-1 | `GET /api/ducks` returns HTTP 200 with `Content-Type: application/json`. |
| FR-2 | Response body is a JSON array of duck objects. Each object contains at minimum: `id` (long), `name` (string), `category` (string — the category name), `priceInCents` (integer), `tagline` (string). |
| FR-3 | When the catalog is empty the response is `[]` (HTTP 200), not an error. |
| FR-4 | Seed data is loaded on startup via a Spring `ApplicationRunner`. The runner is idempotent — re-running the app does not create duplicate ducks. |
| FR-5 | Categories are stored in a separate `category` table; each duck references a category via FK. |

## Non-functional Requirements

| ID | Requirement |
|----|-------------|
| NFR-1 | Database: H2 in-memory (zero external dependencies for workshop participants). |
| NFR-2 | Persistence via Spring Data JPA (Hibernate). |
| NFR-3 | Java 21, Spring Boot 3.x, Maven build. |
| NFR-4 | Response time for the catalog endpoint < 200 ms under no load (sanity, not SLA). |

## Data Model

```
Category
├── id: Long (PK, generated)
└── name: String (unique, not null)

Duck
├── id: Long (PK, generated)
├── name: String (not null)
├── priceInCents: Integer (not null, >= 0)
├── tagline: String (not null)
└── category: Category (ManyToOne, not null)
```

## API Contract

### `GET /api/ducks`

**Response 200:**

```json
[
  {
    "id": 1,
    "name": "Classic Yellow",
    "category": "Classics",
    "priceInCents": 499,
    "tagline": "The one that started it all."
  }
]
```

**Response 200 (empty catalog):**

```json
[]
```

## Acceptance Criteria

1. `GET /api/ducks` returns a JSON array of duck objects with the fields defined in FR-2.
2. Each duck's `category` value is the human-readable category name (not the FK id).
3. The database is seeded with ≥ 10 ducks spanning ≥ 3 categories on first startup.
4. An empty catalog returns `200 []`.
5. A `@WebMvcTest` verifies the controller returns the expected JSON structure.
6. A `@DataJpaTest` verifies the repository correctly queries ducks and joins categories.

## Test Requirements

| Test type | Scope | Key assertions |
|-----------|-------|----------------|
| `@WebMvcTest` | `DuckController` | Returns 200 + correct JSON shape; empty list case. |
| `@DataJpaTest` | `DuckRepository` | Seed data present; FK join resolves category name. |

## Open Questions

_None remaining — all clarifications resolved during spec drafting._
