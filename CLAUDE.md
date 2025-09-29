# DigafMedia Backend Project Guide

## Project Overview

This backend project for DigafMedia follows Clean/Onion Architecture principles with Domain-Driven Design (DDD). The architecture is structured in concentric layers with the domain model at the core.

## Architecture Layers

### Domain Layer (Core)
- Located in `io.github.amenski.digafmedia.domain`
- Contains business entities as Java records (Product, Item, Comment)
- Defines repository interfaces that abstract data access
- Pure business logic with no external dependencies
- Uses immutable value objects (Java records)

### Application Layer (Use Cases)
- Located in `io.github.amenski.digafmedia.usecase`
- Contains concrete use case classes that implement business rules
- Each use case has a single responsibility
- Orchestrates domain entities to implement business logic
- Depends only on the domain layer
- Annotated with Spring's `@Service`

### Infrastructure Layer
- Located in `io.github.amenski.digafmedia.infrastructure`
- Implements interfaces defined in the domain layer
- Provides concrete implementations for external dependencies
- Sub-packages:
  - `persistence`: Database implementations
  - `converter`: Data type converters
  - Controllers: REST endpoints

## Package Structure

```
io.github.amenski.digafmedia
├── domain                      # Domain layer
│   ├── (domain entities)       # Business entities as Java records
│   └── repository              # Repository interfaces
├── usecase                     # Application layer with use cases
├── infrastructure              # Infrastructure layer
│   ├── converter               # Data converters
│   ├── persistence             # Database persistence
│   │   ├── entity              # JPA entities
│   │   └── repository          # Repository implementations
│   └── (controllers)           # REST controllers
```

## Use Cases Implementation

- Each use case is a separate class with a single responsibility
- Use cases follow Command/Query Responsibility Segregation (CQRS)
- Naming convention indicates purpose (e.g., `GetAllItemsUseCase`)
- Use cases are stateless and depend on domain repositories
- Use cases expose an `invoke()` method
- Transaction boundaries defined at use case level

Examples:
- `GetAllItemsUseCase`: Retrieves items, optionally filtered by product
- `CreateCommentUseCase`: Creates a new comment
- `DeleteCommentUseCase`: Deletes a comment by ID
- `GetCommentByIdUseCase`: Retrieves a single comment by ID

## Technologies

- **Spring Boot 3.2.10**: Core framework
- **Spring Data JPA**: Database access and ORM
- **Spring Web**: REST API development
- **Hibernate**: JPA implementation
- **MySQL**: Database system
- **Liquibase**: Database schema migration
- **JodaTime**: Date/time handling (Ethiopian calendar support)
- **Java 17**: Language version
- **Gradle**: Build tool

## Key Architecture Principles

### Domain Model
- Immutable Java records for domain entities
- Value objects with clear semantics
- Domain repositories define data access interfaces

### Repository Pattern
- Domain repositories (interfaces) in `domain.repository`
- Infrastructure implementations in `infrastructure.persistence.repository`
- Dual-layer approach:
  - JPA repositories extend Spring Data interfaces
  - Custom repositories implement domain interfaces

### Dependency Inversion
- Domain and application layers don't depend on infrastructure
- Infrastructure depends on domain interfaces
- Dependencies flow inward in the architecture

### Entity Mapping
- Separation between domain entities and persistence entities
- Mapping logic in repository implementations
- Base entity for common persistence fields

### API Layer
- REST controllers handle HTTP requests/responses
- Business logic delegated to use cases
- Separation of API models from domain models

### Ethiopian Calendar Support
- Special handling for Ethiopian calendar conversion
- Uses JodaTime with `EthiopicChronology`