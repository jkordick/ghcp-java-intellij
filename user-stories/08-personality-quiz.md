# Story 8 — "Which duck are you?" personality quiz

**ID:** `personality-quiz`
**Persona:** Quincy Quacker (customer)
**Priority:** Could (but irresistible)

## Story

> **As** a customer who arrived here from a meme,
> **I want** to take a short personality quiz that recommends a duck for me,
> **so that** I can share the result and accidentally end up buying a duck.

## Acceptance criteria

- `GET /api/quiz/questions` returns 5 to 7 multiple-choice questions.
- Each answer contributes weighted points to one or more duck categories (e.g., picking "I prefer to ask questions rather than answer them" → +2 Philosopher).
- `POST /api/quiz/submit` accepts the answers and returns the single duck whose category has the highest score, with a short personalized message and a link to its detail endpoint.
- Ties are broken deterministically (document the rule in the spec).
- The result for the same set of answers is always the same duck.
- Taking the quiz never modifies any persistent state.

## Out of scope

- A leaderboard.
- Social-media share previews.
- Personalized discount codes.
