Clean Architecture Overview

Layers
- Domain: Core models and value objects. Pure Java, no frameworks.
- Use Cases: Application services and ports (outbound repository interfaces). Depends only on Domain.
- Infrastructure: Adapters (Web, Persistence, Converters) and Spring Boot wiring. Depends on Use Cases and Domain.

Enforced Boundaries
- Domain does not import infrastructure classes.
- Controllers depend on use case classes, not repositories.
- Use case layer defines ports (e.g., repositories) and infrastructure implements them, handling entity-domain mapping.

Key Classes
- Domain: `io.github.amenski.digafmedia.domain.*`
- Use Cases: `io.github.amenski.digafmedia.usecase.*`, ports in `io.github.amenski.digafmedia.usecase.port.*`
- Infrastructure Web: `io.github.amenski.digafmedia.infrastructure.*`
- Infrastructure Persistence: `io.github.amenski.digafmedia.infrastructure.persistence.*`

Recent Changes
- Moved repository interfaces to use case ports (`usecase.port.*`).
- Removed infrastructure type leakage from repositories.
- Added `GetFreeServiceTextUseCase` and updated `GenericController` to depend on it.
- Introduced domain rules: `domain.rules.CommentRules` (validation) and `domain.rules.ItemDefaults` (fallback item).
- Removed persistence-only `Constants` from domain and kept separator within the JPA converter.
 - Introduced rule interfaces: `domain.rules.Validator<T>` and `domain.rules.ItemFallbackPolicy` with default implementations.
 - Infrastructure wires domain rule implementations via `infrastructure.config.DomainRulesConfig`.

Next Steps (Optional)
- If you want stricter purity, remove Spring annotations from use cases and provide configuration in infrastructure to wire them.
- Split layers into separate Gradle modules to enforce boundaries at compile-time.
- Add tests at use case level mocking repositories, and adapter tests for controllers.
