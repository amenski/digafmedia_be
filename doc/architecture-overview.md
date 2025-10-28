# Clean Architecture Overview

## Executive Summary

This project follows **Clean Architecture** (also known as Onion Architecture) combined with **Domain-Driven Design** principles. The architecture emphasizes separation of concerns, testability, and independence from external frameworks and technologies. Business logic remains at the core, insulated from infrastructure concerns.

## System Architecture

### High-Level Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    EXTERNAL INTERFACES                      │
├─────────────────┬─────────────────┬─────────────────────────┤
│   Web Clients   │   Mobile Apps   │   Admin Dashboard       │
└─────────────────┴─────────────────┴─────────────────────────┘
         │                │                │
         └────────────────┼────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   REST API  │  │   GraphQL   │  │   CLI/Console       │  │
│  │ Controllers │  │  Resolvers  │  │   Commands          │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                    APPLICATION LAYER                        │
│  ┌───────────────────────────────────────────────────────┐  │
│  │                    USE CASES                          │  │
│  │  • CreateEntityUseCase    • SearchEntitiesUseCase    │  │
│  │  • UpdateEntityUseCase    • GetEntityByIdUseCase     │  │
│  │  • DeleteEntityUseCase    • ListEntitiesUseCase      │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Domain    │  │  Domain     │  │    Repository       │  │
│  │   Models    │  │  Validators │  │    Interfaces       │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                   INFRASTRUCTURE LAYER                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │Persistence  │  │   External  │  │   Configuration     │  │
│  │Repositories │  │   Services  │  │   & Adapters        │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Core Architectural Principles

### Clean Architecture Implementation

The project strictly follows Clean Architecture with **dependency direction flowing inward**:

- **Domain Layer** (Inner Core): Pure business logic, zero external dependencies
- **Application Layer** (Use Cases): Application-specific business rules, depends only on domain
- **Infrastructure Layer** (Outer Ring): External concerns, depends on inner layers
- **Presentation Layer** (Outermost): User interfaces, depends on application layer

### The Dependency Rule

**Dependencies must point inward only.** Inner layers never depend on outer layers.

```
Infrastructure → Application → Domain
Presentation → Application → Domain
```

### Key Design Patterns

1. **Dependency Inversion Principle**:
   - Domain defines interfaces (ports)
   - Infrastructure provides implementations (adapters)
   - Business logic depends on abstractions, not concretions

2. **Use Case Pattern**:
   - Each use case is a single, focused business operation
   - Self-contained with clear inputs and outputs
   - Orchestrates domain entities to fulfill business requirements

3. **Repository Pattern**:
   - Domain defines repository interfaces
   - Infrastructure implements data access
   - Abstracts persistence mechanism from business logic

4. **Immutable Domain Models**:
   - Domain entities are immutable value objects
   - Changes create new instances
   - Prevents accidental state mutations

## Architecture Layers

### Domain Layer (Core)

**Location**: `domain` package
**Dependencies**: None (pure business logic)

#### Responsibilities
- Define business entities and value objects
- Implement domain validation rules
- Declare repository interfaces
- Contain business logic and invariants

#### Characteristics
- **Zero external dependencies** - no frameworks, no libraries
- **Framework-agnostic** - can be tested without any infrastructure
- **Immutable models** - entities are read-only after creation
- **Self-validating** - validation logic lives within domain

#### Structure
```
domain/
├── Entity.java                 # Domain entities (immutable)
├── ValueObject.java            # Value objects
├── DomainException.java        # Domain-specific exceptions
├── EntityValidator.java        # Validation logic
├── Entities.java              # Collection wrapper (optional)
└── repository/
    └── EntityRepository.java   # Repository interface (port)
```

#### Example Domain Model
```java
public record Product(
    Long id,
    String name,
    String description,
    BigDecimal price,
    OffsetDateTime createdAt
) {
    // Immutable, self-validating domain entity
    // No getters/setters - record provides immutability
    // No infrastructure concerns
}
```

### Application Layer (Use Cases)

**Location**: `usecase` package
**Dependencies**: Domain layer only

#### Responsibilities
- Implement application-specific business rules
- Orchestrate domain entities to fulfill use cases
- Define transaction boundaries
- Coordinate between repositories and services

#### Characteristics
- **Single Responsibility**: Each use case does one thing
- **Stateless**: No instance state between invocations
- **Framework annotations allowed**: Can use DI annotations
- **Testable**: Easy to test with mocked repositories

#### Structure
```
usecase/
├── CreateEntityUseCase.java
├── UpdateEntityUseCase.java
├── DeleteEntityUseCase.java
├── GetEntityByIdUseCase.java
└── GetAllEntitiesUseCase.java
```

#### Use Case Pattern
```java
@Service
public class CreateEntityUseCase {
    private final EntityRepository repository;
    private final EntityValidator validator;

    public Entity invoke(EntityInput input) {
        // 1. Validate input
        validator.validate(input);

        // 2. Create domain entity
        Entity entity = new Entity(/* ... */);

        // 3. Persist via repository
        return repository.save(entity);
    }
}
```

### Infrastructure Layer

**Location**: `infrastructure` package
**Dependencies**: Domain and Application layers

#### Responsibilities
- Implement repository interfaces
- Handle external service integration
- Manage persistence (database, file system, cache)
- Configure framework-specific components
- Provide concrete adapters for ports

#### Characteristics
- **Depends on inner layers** - implements domain interfaces
- **Framework-specific** - uses ORM, HTTP clients, etc.
- **Replaceable** - can swap implementations without affecting business logic
- **Configuration hub** - wires dependencies together

#### Structure
```
infrastructure/
├── persistence/
│   ├── entity/
│   │   ├── EntityJpaEntity.java        # ORM entity
│   │   └── BaseEntity.java              # Common fields
│   └── repository/
│       ├── EntityJpaRepository.java     # Framework repository
│       └── EntityDbRepository.java      # Domain interface impl
├── config/
│   ├── UseCaseConfig.java               # Wire use cases
│   └── DatabaseConfig.java              # Persistence config
└── converter/
    └── EntityConverter.java              # Domain ↔ Persistence mapping
```

#### Repository Implementation Pattern
```java
@Repository
public class EntityDbRepository implements EntityRepository {
    private final EntityJpaRepository jpaRepository;

    @Override
    public Entity save(Entity domain) {
        // Convert domain model to persistence entity
        EntityJpaEntity entity = toEntity(domain);

        // Use framework repository
        EntityJpaEntity saved = jpaRepository.save(entity);

        // Convert back to domain model
        return toDomain(saved);
    }
}
```

### Presentation Layer

**Location**: Varies by interface type (web controllers, CLI, etc.)
**Dependencies**: Application layer (use cases)

#### Responsibilities
- Handle user input/output
- Translate between external formats and domain
- Manage HTTP concerns (status codes, headers)
- Implement API contracts

#### Characteristics
- **Thin layer** - delegates to use cases
- **Protocol-specific** - REST, GraphQL, gRPC, etc.
- **Framework-dependent** - uses web frameworks
- **Separate DTOs** - API models differ from domain models

## Data Flow & Communication Patterns

### Request Processing Flow

```
1. External Request
   ↓
2. Presentation Layer (Controller/Handler)
   - Parse input
   - Validate format
   ↓
3. Application Layer (Use Case)
   - Execute business logic
   - Coordinate domain entities
   ↓
4. Domain Layer
   - Apply business rules
   - Validate invariants
   ↓
5. Infrastructure Layer (Repository)
   - Persist/retrieve data
   ↓
6. Response
   - Map domain to DTO
   - Return to client
```

### Dependency Flow

```
┌─────────────────┐
│  Presentation   │ ────┐
└─────────────────┘     │
                        ↓
┌─────────────────┐   ┌─────────────────┐
│ Infrastructure  │ → │  Application    │
└─────────────────┘   └─────────────────┘
                               │
                               ↓
                      ┌─────────────────┐
                      │     Domain      │
                      └─────────────────┘
```

## Key Benefits

### 1. **Independence from Frameworks**
- Business logic doesn't depend on framework versions
- Can swap frameworks without changing core logic
- Reduces vendor lock-in

### 2. **Testability**
- Domain layer tested without any infrastructure
- Use cases tested with mocked repositories
- Fast, isolated unit tests

### 3. **Flexibility**
- Change database without touching business logic
- Support multiple interfaces (REST, GraphQL, CLI)
- Evolve independently

### 4. **Maintainability**
- Clear boundaries between concerns
- Easy to locate and modify features
- Reduces cognitive load

### 5. **Scalability**
- Business logic can be reused across services
- Microservice-ready architecture
- Clear boundaries for splitting

## Package Structure

```
src/main/java/
└── com.example.application/
    ├── domain/                          # Domain Layer
    │   ├── entity/
    │   │   ├── Entity.java              # Immutable domain models
    │   │   └── EntityValidator.java     # Domain validation
    │   └── repository/
    │       └── EntityRepository.java    # Repository interface (port)
    │
    ├── usecase/                         # Application Layer
    │   ├── CreateEntityUseCase.java     # Business operations
    │   ├── UpdateEntityUseCase.java
    │   └── GetEntityByIdUseCase.java
    │
    └── infrastructure/                  # Infrastructure Layer
        ├── persistence/
        │   ├── entity/
        │   │   └── EntityJpaEntity.java # ORM entities
        │   └── repository/
        │       ├── EntityJpaRepository.java
        │       └── EntityDbRepository.java  # Port implementation
        │
        ├── web/
        │   └── controller/
        │       └── EntityController.java    # REST endpoints
        │
        └── config/
            ├── UseCaseConfig.java       # Dependency wiring
            └── DatabaseConfig.java
```

## Design Principles

### SOLID Principles

1. **Single Responsibility Principle**
   - Each use case has one responsibility
   - Each layer has one reason to change

2. **Open/Closed Principle**
   - Open for extension via interfaces
   - Closed for modification in domain

3. **Liskov Substitution Principle**
   - Repository implementations are interchangeable
   - Domain models are truly immutable

4. **Interface Segregation Principle**
   - Repository interfaces are focused and minimal
   - No fat interfaces

5. **Dependency Inversion Principle**
   - Core principle of Clean Architecture
   - High-level modules don't depend on low-level modules

### Domain-Driven Design

- **Ubiquitous Language**: Domain models reflect business terminology
- **Bounded Contexts**: Clear boundaries between different domains
- **Value Objects**: Immutable, compared by value
- **Aggregates**: Consistency boundaries in domain

## Testing Strategy

### Test Pyramid

```
    ▲
    │   Unit Tests (70%)
    │   - Domain model tests (no dependencies)
    │   - Use case tests with mocked repositories
    │   - Pure business logic validation
    │
    │   Integration Tests (20%)
    │   - Repository implementations with test database
    │   - Use case orchestration
    │   - Infrastructure component interaction
    │
    │   End-to-End Tests (10%)
    │   - Full API flow tests
    │   - User journey scenarios
    └───────────────────────────▶
```

### Testing Benefits

- **Domain Layer**: Test without any infrastructure
- **Application Layer**: Test with mocked repositories
- **Infrastructure Layer**: Test with real dependencies or test doubles
- **Fast feedback**: Unit tests run in milliseconds

## Evolution & Extensibility

### Adding New Features

1. **Define domain model** in domain layer
2. **Create validator** for business rules
3. **Define repository interface** (port)
4. **Implement use cases** in application layer
5. **Implement repository** in infrastructure
6. **Add presentation layer** (controllers, DTOs)

### Changing Persistence

- Only infrastructure layer changes
- Domain and use cases remain untouched
- Implement new repository adapter

### Supporting New Interfaces

- Add new presentation layer
- Reuse existing use cases
- No changes to business logic

### Microservices Migration

- Bounded contexts become microservices
- Use cases can be distributed
- Domain models can be shared or duplicated

## Common Pitfalls to Avoid

1. **Domain depending on infrastructure**: Never import infrastructure in domain
2. **Anemic domain model**: Put business logic in domain, not use cases
3. **Fat use cases**: Keep use cases focused on orchestration
4. **Leaky abstractions**: Repository interfaces shouldn't expose ORM details
5. **Mutable domain models**: Keep domain entities immutable
6. **Business logic in controllers**: Delegate everything to use cases

## Best Practices

1. **Keep domain pure**: No framework annotations in domain
2. **Use immutable models**: Prefer records or immutable classes
3. **One use case, one responsibility**: Don't create god classes
4. **Test domain independently**: Unit tests should be fast
5. **Map at boundaries**: Convert between domain and persistence models
6. **Validate in domain**: Business rules belong in domain layer
7. **Thin controllers**: Controllers should only handle HTTP concerns

---

*This architecture overview provides a technology-agnostic reference for implementing Clean Architecture. The principles apply regardless of programming language, framework, or infrastructure choices.*