# Story 9 — Web frontend

**ID:** `web-frontend`
**Persona:** Quincy Quacker (customer)
**Priority:** Should

## Story

> **As** a customer visiting The Rubber Duck Emporium,
> **I want** a web-based user interface served by the same application,
> **so that** I can browse ducks, manage my cart, take the quiz, and check out — all from a browser without needing curl or Postman.

## Acceptance criteria

- The Spring Boot app serves a single-page HTML frontend as a static resource at `GET /` (from `src/main/resources/static/`).
- The frontend consumes the existing JSON API endpoints — no new backend routes are required.
- **Catalog page**: displays all ducks with name, category, price, and tagline. Includes search/filter controls (free text, category dropdown, price range).
- **Duck detail view**: shows full description, personality traits, stock status, and an "Add to Cart" button.
- **Duck of the Day**: prominently featured on the catalog page or its own section.
- **Cart**: shows current items with quantities, line totals, and a running total. Supports quantity changes and item removal. Includes a "Proceed to Checkout" action.
- **Checkout form**: collects shipping name, email, address, and a (mocked) card number. Displays the order confirmation on success. Shows validation errors inline.
- **Personality quiz**: presents questions and displays the recommended duck with its message.
- The frontend requires no build step — vanilla HTML/CSS/JS served as static assets.
- The UI is usable on both desktop and mobile viewports (responsive layout).
- API errors (400, 404, 409) are displayed to the user in a human-friendly way.

## Out of scope

- Server-side rendering or Thymeleaf templates.
- A JavaScript framework that requires a build pipeline (React, Vue, etc. are overkill here).
- Authentication UI for Dr. Mallard's admin endpoints — admin stays curl-only.
- Image assets for ducks — text/emoji placeholders are fine.
- Offline support or service workers.

## Notes

- Place the frontend at `src/main/resources/static/index.html`.
- Use `fetch()` against the existing API. Served from the same origin, so no CORS issues.
- This story depends on all API stories (1–8) being complete.
