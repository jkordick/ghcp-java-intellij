# Project: duck-emporium

- Language: Java 21. Framework: Spring Boot 3.x (Spring MVC). Build: Maven.
- Use Spring Data JPA with H2 in-memory database (dev) or SQLite via JDBC (persistence).
- Tests live in `src/test/java/` as `*Test.java`. Run with `mvn test`.
- Use JUnit 5 + Mockito. Use `MockMvc` for controller (slice) tests.
- User stories live in `../user-stories/`. Specs go in `specs/<story-id>/`.
- Never edit `user-stories/**`.
- Only edit files under `specs/**` when invoked through an `sdd-*` prompt.
- Follow the workflow in `.github/prompts/sdd-*.prompt.md`.
- Payments are MOCKED. Never integrate a real payment provider.