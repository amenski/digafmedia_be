# Repository Guidelines

## Project Structure & Module Organization
- Source: `src/main/java/io/github/amenski/digafmedia/` using Clean Architecture with `domain/`, `usecase/`, and `infrastructure/` packages.
- Resources: `src/main/resources/` (config, database migrations under `db/sql/`, templates/static).
- Tests: `src/test/java/io/github/amenski/digafmedia/` mirroring main packages; test resources in `src/test/resources/`.
- Build/config: `build.gradle`, application configuration in `resources/application.yaml`.

## Build, Test, and Development Commands
- `./gradlew build`: Compile, run checks, and all tests.
- `./gradlew test`: Execute unit/integration tests only.
- `./gradlew bootRun`: Start the app locally (default profile).
- Profile override: Use `application-dev.yaml` for development or set active profiles.

## Coding Style & Naming Conventions
- Java 17+, standard indentation, no wildcard imports.
- Clean boundaries: domain is pure (no Spring/annotations); controllers call use cases only.
- Naming: domain models `Comment`, `Item`, `Product`; repositories `[Entity]Repository`; JPA entities `[Entity]Entity`; use cases `[Verb][Noun]UseCase` (e.g., `CreateCommentUseCase`); controllers `[Resource]Controller`.
- Domain rules: Implement validators in `domain/rules/` package (e.g., `CommentValidatorDefault`).

## Testing Guidelines
- Frameworks: JUnit 5, Mockito; prefer fast, deterministic tests.
- Location: mirror structure under `src/test/java/`; name tests `*Test.java`.
- Integration: test with MySQL database when persistence is needed.
- Run locally: `./gradlew test` (CI runs `build`).

## Commit & Pull Request Guidelines
- Commits: imperative mood with tags like `feat:`, `fix:`, `chore:`, `docs:`; reference issue keys when applicable.
- PRs: clear description, linked issues, testing notes, and API examples. Ensure build green and tests added/updated.

## Security & Configuration Tips
- Do not commit secrets; use environment variables. Validate inputs and follow standard error responses.
- Enable structured logging with correlation IDs; rate limit public endpoints using `RateLimitingFilter`.
- Database: MySQL with Hikari connection pool; auto-commit disabled for better transaction control.

## Architecture Guardrails
- Dependency direction: `domain → usecase → infrastructure`.
- Ports/adapters: define ports in `usecase.port`; implement in `infrastructure.*`; repositories live only in infrastructure.
- Forbidden: repositories in controllers; use cases importing Spring MVC; domain importing persistence/web/security.
- Domain validation: use custom validators in domain rules; throw `DomainValidationException` for business rule violations.

## Web/API Standards
- DTOs: use entity models in controllers; avoid inline DTOs.
- Pagination: implement paginated responses for list endpoints.
- Validation: use domain validators for business rules; Spring validation for input formatting.
- OpenAPI: document endpoints with SpringDoc OpenAPI; configure via `OpenApiConfig`.
- Filtering: implement domain-level filtering through use cases.

## Security & Auth
- Current implementation: basic web security with correlation ID tracking.
- Authorization: implement role-based access control as needed.
- Data access: all writes should go through use cases; controllers never bypass use cases.

## Error Handling
- Standard: use Spring's error handling mechanisms.
- Mapping: validation → 400; not found → 404; access denied → 403; conflict/domain rule → 409; unexpected → 500.
- Response shape: return appropriate HTTP status codes with meaningful messages.

## Testing Expectations
- Use cases: unit tests with mocked ports; deterministic and fast.
- Controllers: MockMvc tests for validation, security, and basic contract checks.
- Persistence: repository/integration tests with actual database; verify migrations.

## PR Checklist (Must Pass)
- Validations present for domain rules; sensible defaults set.
- OpenAPI documentation for endpoints.
- No repositories in controllers; domain logic in use cases.
- New/changed endpoints covered by tests; error mappings verified.
- Domain rules implemented and tested separately from infrastructure.

## Database & Migration Guidelines
- Database: MySQL with JPA/Hibernate for ORM.
- Migrations: Liquibase configured but currently disabled; manual SQL migrations in `resources/db/sql/`.
- Entity naming: use `[Entity]Entity` for JPA entities (e.g., `CommentEntity`).
- Conversion: use attribute converters for complex type mappings (e.g., `StringToListAttributeConverter`).

## Domain Rules Implementation
- Validators: implement in `domain/rules/` package (e.g., `Validator` interface).
- Fallback policies: implement fallback logic for domain objects (e.g., `ItemFallbackPolicy`).
- Defaults: define default values and behaviors in domain rules (e.g., `ItemDefaults`).
