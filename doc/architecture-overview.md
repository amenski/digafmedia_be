# Kontashop Architecture Overview

## Executive Summary

Kontashop is a modern marketplace platform built with **Clean Architecture** principles, designed for scalability, maintainability, and extensibility. The system implements a robust domain-driven design with clear separation of concerns across domain, use case, and infrastructure layers.

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
│                    API GATEWAY LAYER                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   REST API  │  │   Web MVC   │  │   Security Filter   │  │
│  │ Controllers │  │  Framework  │  │     Chain          │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                    APPLICATION LAYER                        │
│  ┌───────────────────────────────────────────────────────┐  │
│  │                    USE CASES                          │  │
│  │  • CreateListingUseCase    • SearchListingsUseCase    │  │
│  │  • UpdateListingUseCase    • GetUserProfileUseCase    │  │
│  │  • UploadMediaUseCase      • AnalyticsUseCases        │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Domain    │  │  Domain     │  │    Specifications   │  │
│  │   Models    │  │  Events     │  │   & Business Rules  │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────────────┐
│                   INFRASTRUCTURE LAYER                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │Persistence  │  │   External  │  │   File Storage      │  │
│  │ (PostgreSQL)│  │   Services  │  │   & Media           │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Core Architectural Principles

### Clean Architecture Implementation

The project strictly follows Clean Architecture with dependency direction flowing inward:

- **Domain Layer** (Inner Core): Pure business logic, no external dependencies
- **Use Case Layer**: Application-specific business rules, depends only on domain
- **Infrastructure Layer**: External concerns, depends on both inner layers

### Key Design Patterns

1. **Ports and Adapters**: Repository interfaces in [`usecase.port.repository`](src/main/java/io/kontashop/usecase/port/repository/), implementations in [`infrastructure.persistence.repository`](src/main/java/io/kontashop/infrastructure/persistence/repository/)
2. **CQRS**: Separate commands and queries for listing operations
3. **Domain Events**: Event-driven architecture for cross-cutting concerns
4. **Specification Pattern**: Business rules encapsulation in domain layer

## Technology Stack

### Backend Framework
- **Spring Boot 3.5.3** with Java 17
- **Spring Security** for authentication and authorization
- **Spring Data JPA** for data access
- **Spring Cache** with Caffeine for performance

### Database & Persistence
- **PostgreSQL** 15+ as primary database
- **Liquibase** for database migrations
- **Hibernate** as JPA implementation
- **H2** for testing

### Security & Authentication
- **JWT** (jjwt) for stateless authentication
- **Spring Security** for RBAC and method-level security
- **Bucket4j** for rate limiting
- **Apache Commons Text** for input sanitization

### API & Documentation
- **RESTful API** with JSON payloads
- **SpringDoc OpenAPI** for API documentation
- **RFC 7807** (Problem Detail) for error responses

### Media & File Handling
- **Local file storage** with thumbnail generation
- **Apache Tika** for MIME type detection
- **Thumbnailator** for image processing

## Key Components

### Domain Layer ([`src/main/java/io/kontashop/domain/`](src/main/java/io/kontashop/domain/))

#### Core Domain Models
- **`Listing`**: Marketplace listings with flexible attributes system
- **`User`**: User accounts with role-based access control
- **`Category`**: Hierarchical categorization system
- **`Media`**: File and image management for listings
- **`Favorite`**: User favorites/bookmarks system

#### Domain Events
- **`ListingViewedEvent`**: Track listing view analytics
- **`SearchCapturedEvent`**: Capture search trends and suggestions

#### Business Rules & Specifications
- **`CompositeSpecification`**: Reusable business rule composition
- **Validation Specifications**: Price, currency, title validation rules

### Use Case Layer ([`src/main/java/io/kontashop/usecase/`](src/main/java/io/kontashop/usecase/))

#### Core Business Operations
- **Listing Management**: [`CreateListingUseCase`](src/main/java/io/kontashop/usecase/listing/CreateListingUseCase.java), [`UpdateListingUseCase`](src/main/java/io/kontashop/usecase/listing/UpdateListingUseCase.java), [`SearchListingsUseCase`](src/main/java/io/kontashop/usecase/listing/SearchListingsUseCase.java)
- **User Management**: Authentication, profile management, favorites
- **Media Management**: Upload, serve, and process listing media
- **Analytics**: Admin dashboards, trending calculations, performance metrics

#### Ports (Interfaces)
- **Repository Ports**: [`ListingRepository`](src/main/java/io/kontashop/usecase/port/repository/ListingRepository.java), [`UserRepository`](src/main/java/io/kontashop/usecase/port/repository/UserRepository.java), etc.
- **External Service Ports**: File storage, notification, outbox patterns

### Infrastructure Layer ([`src/main/java/io/kontashop/infrastructure/`](src/main/java/io/kontashop/infrastructure/))

#### Persistence
- **JPA Entities**: [`ListingJpaEntity`](src/main/java/io/kontashop/infrastructure/persistence/entity/ListingJpaEntity.java), [`UserJpaEntity`](src/main/java/io/kontashop/infrastructure/persistence/entity/UserJpaEntity.java)
- **Repository Implementations**: Database-specific implementations of port interfaces
- **Mappers**: Convert between domain models and persistence entities

#### Web Layer
- **REST Controllers**: Handle HTTP requests and responses
- **DTOs**: Data transfer objects for API contracts
- **Exception Handling**: Global exception handling with proper HTTP status codes

#### External Services
- **File Storage**: Local file system implementation
- **Event Publishing**: Spring ApplicationEvent-based implementation
- **Email Notifications**: SMTP-based notification system

## Data Flow & Communication Patterns

### Request Processing Flow

1. **HTTP Request** → Security Filters (Rate Limiting, Authentication)
2. **Controller** → Validation & DTO Mapping
3. **Use Case** → Business Logic Execution
4. **Domain** → Business Rule Validation
5. **Repository** → Data Persistence/Retrieval
6. **Response** → DTO Mapping & HTTP Response

### Event-Driven Architecture

```
Listing Created → Domain Event → Event Handler → Analytics Update
                ↘ Event Handler → Email Notification
                ↘ Event Handler → Search Index Update
```

### Outbox Pattern
- **`OutboxEvent`**: Reliable event processing for external systems
- **`OutboxProcessor`**: Background processing of domain events

## Security Architecture

### Authentication & Authorization
- **JWT-based** stateless authentication
- **Role-Based Access Control** (USER, ADMIN)
- **Method-level security** with `@PreAuthorize`
- **HttpOnly cookies** for secure token storage

### Input Validation & Sanitization
- **Spring Validation** with custom validators
- **Input sanitization** to prevent XSS attacks
- **File type validation** for uploads

### Rate Limiting
- **IP-based rate limiting** with configurable buckets
- **Endpoint-specific limits** for sensitive operations
- **429 Too Many Requests** responses

## Testing Strategy

### Test Pyramid Implementation
```
    ▲
    │   Unit Tests (70%)
    │   - Use Case tests with mocked dependencies
    │   - Domain model tests
    │
    │   Integration Tests (20%)
    │   - Controller tests with MockMvc
    │   - Repository tests with Testcontainers
    │
    │   End-to-End Tests (10%)
    │   - Full API flow tests
    └───────────────────────────▶
```

### Testing Technologies
- **JUnit 5** for test framework
- **Mockito** for mocking dependencies
- **Testcontainers** for database integration tests
- **Spring Boot Test** for application context testing

## Deployment Architecture

### Containerized Deployment
```yaml
# docker-compose.yml
services:
  app:
    image: kontashop:latest
    environment:
      - DB_URL=jdbc:postgresql://db:5432/kontashop
      - JWT_SECRET=${JWT_SECRET}
    volumes:
      - ./uploads:/app/uploads
      - ./logs:/app/logs
  
  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=kontashop
    volumes:
      - postgres_data:/var/lib/postgresql/data
```

### Traditional VPS Deployment
- **Systemd service** for application management
- **Nginx reverse proxy** for static files and SSL termination
- **PostgreSQL** with connection pooling

## Performance & Scalability

### Caching Strategy
- **Caffeine** in-memory cache for frequently accessed data
- **Category data** caching to reduce database load
- **Configurable TTL** and size limits

### Database Optimization
- **Proper indexing** on searchable columns
- **Connection pooling** via HikariCP
- **Query optimization** with JPA Criteria API

### Monitoring & Observability
- **Spring Boot Actuator** for health checks
- **Structured logging** with correlation IDs
- **Business metrics** exposure for monitoring

## Future Evolution & Extensibility

### Plugin Architecture
- **Flexible attribute system** for different listing types
- **Custom validation rules** per category
- **Extensible filter definitions**

### Microservices Readiness
- **Clear bounded contexts** within monolithic application
- **Event-driven communication** patterns
- **Database per service** preparation

### API Evolution
- **Versioned API endpoints** (`/v1/`)
- **Backward-compatible changes** with deprecation headers
- **OpenAPI documentation** for client generation

---

*This architecture overview provides a comprehensive reference for understanding the Kontashop system design, implementation patterns, and future evolution paths. For detailed implementation guidelines, refer to [`CLAUDE.md`](CLAUDE.md) and [`AGENTS.md`](docs/AGENTS.md).*