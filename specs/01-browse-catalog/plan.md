# Plan — Browse the Duck Catalog

**Spec:** `specs/01-browse-catalog/spec.md`
**Status:** Draft

---

## 1. Data Model (JPA Entities)

### `Category`

```java
@Entity
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
```

### `Duck`

```java
@Entity
@Table(name = "duck")
public class Duck {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer priceInCents;

    @Column(nullable = false)
    private String tagline;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
```

### DTO (read-only projection)

```java
public record DuckDto(
    Long id,
    String name,
    String category,   // category.name
    Integer priceInCents,
    String tagline
) {}
```

---

## 2. Package / Class Layout

```
duck-emporium/src/main/java/org/duckemporium/
├── DuckEmporiumApplication.java          // @SpringBootApplication
├── catalog/
│   ├── Category.java                     // JPA entity
│   ├── CategoryRepository.java           // Spring Data JPA
│   ├── Duck.java                         // JPA entity
│   ├── DuckRepository.java              // Spring Data JPA
│   ├── DuckDto.java                     // response record
│   ├── DuckController.java             // REST controller
│   ├── DuckService.java                // business logic (thin)
│   └── DataSeeder.java                 // ApplicationRunner

duck-emporium/src/main/resources/
├── application.properties               // H2 config, JPA settings

duck-emporium/src/test/java/org/duckemporium/catalog/
├── DuckControllerTest.java              // @WebMvcTest
└── DuckRepositoryTest.java             // @DataJpaTest
```

---

## 3. REST Controller Interface

```java
@RestController
@RequestMapping("/api/ducks")
public class DuckController {

    @GetMapping
    public List<DuckDto> listAll() { ... }
}
```

- Always returns `200 OK`.
- Delegates to `DuckService.findAll()`.
- No request parameters for this story.

---

## 4. Service Layer

```java
@Service
public class DuckService {

    public List<DuckDto> findAll() {
        // Fetch all ducks (eagerly join category), map to DuckDto
    }
}
```

Mapping from entity → DTO can be done inline (constructor call) — no need for a mapping library at this scale.

---

## 5. Repository Layer

```java
public interface DuckRepository extends JpaRepository<Duck, Long> {
    @Query("SELECT d FROM Duck d JOIN FETCH d.category")
    List<Duck> findAllWithCategory();
}

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
```

`findAllWithCategory()` avoids N+1 queries on the `@ManyToOne` relationship.

---

## 6. Seed Data (DataSeeder)

```java
@Component
public class DataSeeder implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        if (duckRepository.count() > 0) return; // idempotent guard
        // Create 3+ categories, then 10+ ducks referencing them
    }
}
```

Categories: "Classics", "Themed", "Luxury" (minimum). Ducks distributed across them.

---

## 7. External Dependencies (Maven)

Add to `duck-emporium/pom.xml`:

| Dependency | Coordinates | Purpose |
|-----------|-------------|---------|
| Spring Boot Starter Web | `org.springframework.boot:spring-boot-starter-web` | REST + embedded Tomcat |
| Spring Boot Starter Data JPA | `org.springframework.boot:spring-boot-starter-data-jpa` | JPA + Hibernate |
| H2 Database | `com.h2database:h2` (runtime) | In-memory DB |
| Spring Boot Starter Test | `org.springframework.boot:spring-boot-starter-test` (test) | JUnit 5, Mockito, MockMvc |

Parent POM: `org.springframework.boot:spring-boot-starter-parent:3.4.x`

---

## 8. Testing Strategy

### 8.1 Controller — `DuckControllerTest`

- **Type:** `@WebMvcTest(DuckController.class)`
- **Mocks:** `DuckService` via `@MockBean`
- **Cases:**
  1. Service returns list of 2 ducks → assert 200, JSON array length 2, field presence.
  2. Service returns empty list → assert 200, `[]`.

### 8.2 Repository — `DuckRepositoryTest`

- **Type:** `@DataJpaTest`
- **Setup:** Insert categories + ducks via repository in `@BeforeEach`.
- **Cases:**
  1. `findAllWithCategory()` returns all seeded ducks.
  2. Returned ducks have non-null category with expected name.

---

## 9. Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:h2:mem:duckdb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
```

---

## 10. Risks & Mitigations

| Risk | Impact | Mitigation |
|------|--------|------------|
| N+1 queries on category fetch | Slow response at scale | `JOIN FETCH` in repository query |
| Seed data inserted twice on fast restart | Duplicate ducks | Idempotent guard (`count() > 0`) |
| H2 dialect quirks vs production DB | Behavior differences | Acceptable for workshop scope; note in docs |

---

## Implementation Order

1. Update `pom.xml` with Spring Boot parent + dependencies.
2. Create `DuckEmporiumApplication.java`.
3. Create `Category` entity + `CategoryRepository`.
4. Create `Duck` entity + `DuckRepository`.
5. Create `DuckDto` record.
6. Create `DuckService`.
7. Create `DuckController`.
8. Create `DataSeeder`.
9. Add `application.properties`.
10. Write `DuckControllerTest`.
11. Write `DuckRepositoryTest`.
12. Verify: `mvn test` passes, manual `curl` against running app.
