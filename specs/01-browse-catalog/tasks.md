# Tasks — Browse the Duck Catalog

**Plan:** `specs/01-browse-catalog/plan.md`

---

## Task 1 — Bootstrap Spring Boot project

Update `duck-emporium/pom.xml` to use `spring-boot-starter-parent` (3.4.x) and add dependencies: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `com.h2database:h2` (runtime), `spring-boot-starter-test` (test). Create `src/main/java/org/duckemporium/DuckEmporiumApplication.java` with `@SpringBootApplication` and a `main` method. Add `src/main/resources/application.properties` with H2 datasource config.

**Depends on:** —
**Acceptance check:** `mvn spring-boot:run` starts without errors and shuts down cleanly with Ctrl-C.

---

## Task 2 — Create Category entity and repository

Create `src/main/java/org/duckemporium/catalog/Category.java` (JPA entity with `id`, `name`) and `src/main/java/org/duckemporium/catalog/CategoryRepository.java` (extends `JpaRepository`, includes `findByName`).

**Depends on:** Task 1
**Acceptance check:** `mvn compile` succeeds.

---

## Task 3 — Create Duck entity and repository

Create `src/main/java/org/duckemporium/catalog/Duck.java` (JPA entity with `id`, `name`, `priceInCents`, `tagline`, `@ManyToOne category`) and `src/main/java/org/duckemporium/catalog/DuckRepository.java` (extends `JpaRepository`, includes `findAllWithCategory()` with `JOIN FETCH`).

**Depends on:** Task 2
**Acceptance check:** `mvn compile` succeeds.

---

## Task 4 — Create DuckDto record

Create `src/main/java/org/duckemporium/catalog/DuckDto.java` — a Java record with fields: `id`, `name`, `category`, `priceInCents`, `tagline`.

**Depends on:** —
**Acceptance check:** `mvn compile` succeeds.

---

## Task 5 — Create DuckService

Create `src/main/java/org/duckemporium/catalog/DuckService.java`. Inject `DuckRepository`, implement `findAll()` returning `List<DuckDto>` by calling `findAllWithCategory()` and mapping entities to DTOs.

**Depends on:** Task 3, Task 4
**Acceptance check:** `mvn compile` succeeds.

---

## Task 6 — Create DuckController

Create `src/main/java/org/duckemporium/catalog/DuckController.java`. `@RestController` mapped to `/api/ducks` with a single `@GetMapping` method delegating to `DuckService.findAll()`.

**Depends on:** Task 5
**Acceptance check:** `mvn compile` succeeds.

---

## Task 7 — Create DataSeeder

Create `src/main/java/org/duckemporium/catalog/DataSeeder.java`. Implements `ApplicationRunner`. On startup, checks `duckRepository.count()` — if 0, inserts ≥ 3 categories and ≥ 10 ducks.

**Depends on:** Task 3
**Acceptance check:** `mvn spring-boot:run`, then `curl http://localhost:8080/api/ducks` returns ≥ 10 ducks across ≥ 3 categories.

---

## Task 8 — Write DuckControllerTest

Create `src/test/java/org/duckemporium/catalog/DuckControllerTest.java`. `@WebMvcTest(DuckController.class)` with `@MockBean DuckService`. Test cases: (1) returns 200 + JSON array with correct fields; (2) empty list returns `[]`.

**Depends on:** Task 6
**Acceptance check:** `mvn test -Dtest=DuckControllerTest` passes.

---

## Task 9 — Write DuckRepositoryTest

Create `src/test/java/org/duckemporium/catalog/DuckRepositoryTest.java`. `@DataJpaTest` that inserts test data in `@BeforeEach`. Test cases: (1) `findAllWithCategory()` returns expected count; (2) returned ducks have non-null category name.

**Depends on:** Task 3
**Acceptance check:** `mvn test -Dtest=DuckRepositoryTest` passes.

---

## Task 10 — Full verification

Run `mvn test` — all tests pass. Start the app and verify `curl http://localhost:8080/api/ducks` returns the expected JSON shape.

**Depends on:** Task 7, Task 8, Task 9
**Acceptance check:** `mvn test` exits with `BUILD SUCCESS`.
