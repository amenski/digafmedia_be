# NGO Platform: Immediate Backend Implementation Steps

## Executive Summary

Based on the comprehensive architecture review and existing Clean Architecture foundation, here are the specific, actionable next steps for implementing the NGO platform backend. This document focuses on immediate coding tasks, implementation priorities, and integration strategies.

## Phase 1: Foundation Implementation (Week 1-2)

### Week 1: Database & Domain Layer

#### 1.1 Database Schema Implementation
**Priority: CRITICAL**

```sql
-- File: src/main/resources/db/sql/0003.create_ngo_tables.sql
-- Create tables for all five NGO sections

-- Afalgun (Lost Person Search)
CREATE TABLE afalgun_post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    missing_person_name VARCHAR(255) NOT NULL,
    age INT,
    last_seen_location VARCHAR(500),
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(255),
    description TEXT,
    status ENUM('ACTIVE', 'FOUND', 'CLOSED') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Irdata (Crowdfunding Assistance)
CREATE TABLE irdata_post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    goal_amount DECIMAL(15,2),
    current_amount DECIMAL(15,2) DEFAULT 0,
    bank_name VARCHAR(255),
    account_number VARCHAR(100),
    account_holder VARCHAR(255),
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(255),
    status ENUM('ACTIVE', 'FUNDED', 'CLOSED') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tikoma (Community Alerts)
CREATE TABLE tikoma_alert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    urgency ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Free-Service (Service Directory)
CREATE TABLE free_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    provider_name VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(500),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(255),
    category VARCHAR(100),
    hours_of_operation VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- With-You (Testimonials)
CREATE TABLE with_you_testimonial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    story TEXT NOT NULL,
    author_name VARCHAR(255),
    author_location VARCHAR(255),
    is_approved BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### 1.2 Domain Models Implementation
**Priority: CRITICAL**

```java
// File: src/main/java/io/github/amenski/digafmedia/domain/afalgun/AfalgunPost.java
package io.github.amenski.digafmedia.domain.afalgun;

import java.time.OffsetDateTime;

public record AfalgunPost(
    Long id,
    String missingPersonName,
    Integer age,
    String lastSeenLocation,
    String contactName,
    String contactPhone,
    String contactEmail,
    String description,
    AfalgunStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}

// File: src/main/java/io/github/amenski/digafmedia/domain/afalgun/AfalgunStatus.java
package io.github.amenski.digafmedia.domain.afalgun;

public enum AfalgunStatus {
    ACTIVE, FOUND, CLOSED
}
```

**Repeat this pattern for all five sections:**
- `domain/irdata/IrdataPost.java` and `IrdataStatus.java`
- `domain/tikoma/TikomaAlert.java` and `Urgency.java`
- `domain/freeservice/FreeService.java`
- `domain/withyou/WithYouTestimonial.java`

#### 1.3 Repository Ports Implementation
**Priority: HIGH**

```java
// File: src/main/java/io/github/amenski/digafmedia/usecase/port/AfalgunRepository.java
package io.github.amenski.digafmedia.usecase.port;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import java.util.List;
import java.util.Optional;

public interface AfalgunRepository {
    List<AfalgunPost> findAllActive();
    Optional<AfalgunPost> findById(Long id);
    AfalgunPost save(AfalgunPost post);
    void updateStatus(Long id, AfalgunStatus status);
}
```

**Create repository ports for all five sections following the same pattern.**

### Week 2: Infrastructure & Use Cases

#### 2.1 JPA Entity Implementation
**Priority: HIGH**

```java
// File: src/main/java/io/github/amenski/digafmedia/infrastructure/persistence/entity/AfalgunPostEntity.java
package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "afalgun_post")
public class AfalgunPostEntity extends BaseEntity {
    
    @Column(name = "missing_person_name", nullable = false)
    private String missingPersonName;
    
    private Integer age;
    
    @Column(name = "last_seen_location")
    private String lastSeenLocation;
    
    @Column(name = "contact_name")
    private String contactName;
    
    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Column(name = "contact_email")
    private String contactEmail;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    private AfalgunStatus status = AfalgunStatus.ACTIVE;
    
    // Getters and setters following existing pattern
}
```

#### 2.2 Repository Implementation
**Priority: HIGH**

```java
// File: src/main/java/io/github/amenski/digafmedia/infrastructure/persistence/repository/AfalgunDbRepository.java
package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.usecase.port.AfalgunRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.AfalgunPostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AfalgunDbRepository implements AfalgunRepository {
    
    private final AfalgunJpaRepository afalgunJpaRepository;
    
    public AfalgunDbRepository(AfalgunJpaRepository afalgunJpaRepository) {
        this.afalgunJpaRepository = afalgunJpaRepository;
    }
    
    @Override
    public List<AfalgunPost> findAllActive() {
        return afalgunJpaRepository.findByStatus(AfalgunStatus.ACTIVE)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    // Implement other methods following CommentDbRepository pattern
}
```

#### 2.3 Use Case Implementation
**Priority: HIGH**

```java
// File: src/main/java/io/github/amenski/digafmedia/usecase/afalgun/CreateAfalgunPostUseCase.java
package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.usecase.port.AfalgunRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CreateAfalgunPostUseCase {
    
    private final AfalgunRepository afalgunRepository;
    
    public CreateAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }
    
    public AfalgunPost invoke(AfalgunPost post) {
        // Set creation timestamp if missing
        AfalgunPost toPersist = post;
        if (post.createdAt() == null) {
            toPersist = new AfalgunPost(
                post.id(),
                post.missingPersonName(),
                post.age(),
                post.lastSeenLocation(),
                post.contactName(),
                post.contactPhone(),
                post.contactEmail(),
                post.description(),
                post.status(),
                OffsetDateTime.now(),
                null
            );
        }
        return afalgunRepository.save(toPersist);
    }
}
```

## Phase 2: API Layer & Validation (Week 3)

### 3.1 Controller Implementation
**Priority: HIGH**

```java
// File: src/main/java/io/github/amenski/digafmedia/infrastructure/web/controller/AfalgunController.java
package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.usecase.afalgun.CreateAfalgunPostUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.GetActiveAfalgunPostsUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.UpdateAfalgunPostStatusUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/afalgun")
public class AfalgunController {
    
    private final GetActiveAfalgunPostsUseCase getActivePostsUseCase;
    private final CreateAfalgunPostUseCase createPostUseCase;
    private final UpdateAfalgunPostStatusUseCase updateStatusUseCase;
    
    public AfalgunController(GetActiveAfalgunPostsUseCase getActivePostsUseCase,
                           CreateAfalgunPostUseCase createPostUseCase,
                           UpdateAfalgunPostStatusUseCase updateStatusUseCase) {
        this.getActivePostsUseCase = getActivePostsUseCase;
        this.createPostUseCase = createPostUseCase;
        this.updateStatusUseCase = updateStatusUseCase;
    }
    
    @GetMapping("/posts")
    public ResponseEntity<List<AfalgunPost>> getActivePosts() {
        try {
            List<AfalgunPost> posts = getActivePostsUseCase.invoke();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/posts")
    public ResponseEntity<AfalgunPost> createPost(@RequestBody AfalgunPostRequest request) {
        try {
            AfalgunPost post = new AfalgunPost(
                null,
                request.missingPersonName(),
                request.age(),
                request.lastSeenLocation(),
                request.contactName(),
                request.contactPhone(),
                request.contactEmail(),
                request.description(),
                AfalgunStatus.ACTIVE,
                null,
                null
            );
            AfalgunPost created = createPostUseCase.invoke(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    public record AfalgunPostRequest(
        String missingPersonName,
        Integer age,
        String lastSeenLocation,
        String contactName,
        String contactPhone,
        String contactEmail,
        String description
    ) {}
}
```

### 3.2 Configuration Updates
**Priority: MEDIUM**

```java
// File: src/main/java/io/github/amenski/digafmedia/infrastructure/config/NgoUseCaseConfig.java
package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.usecase.afalgun.*;
import io.github.amenski.digafmedia.usecase.port.AfalgunRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NgoUseCaseConfig {
    
    @Bean
    public GetActiveAfalgunPostsUseCase getActiveAfalgunPostsUseCase(AfalgunRepository afalgunRepository) {
        return new GetActiveAfalgunPostsUseCase(afalgunRepository);
    }
    
    @Bean
    public CreateAfalgunPostUseCase createAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
        return new CreateAfalgunPostUseCase(afalgunRepository);
    }
    
    // Repeat for all use cases across five sections
}
```

## Integration Strategies

### 1. Leveraging Existing Patterns
- **Repository Pattern**: Follow [`CommentDbRepository`](src/main/java/io/github/amenski/digafmedia/infrastructure/persistence/repository/CommentDbRepository.java) implementation
- **Use Case Pattern**: Follow [`CreateCommentUseCase`](src/main/java/io/github/amenski/digafmedia/usecase/CreateCommentUseCase.java) structure
- **Controller Pattern**: Follow [`CommentController`](src/main/java/io/github/amenski/digafmedia/infrastructure/web/controller/CommentController.java) design

### 2. Package Structure Enhancement
```
src/main/java/io/github/amenski/digafmedia/
├── domain/
│   ├── afalgun/           # Lost person search
│   ├── irdata/            # Crowdfunding assistance  
│   ├── tikoma/            # Community alerts
│   ├── freeservice/       # Service directory
│   └── withyou/           # Testimonials
├── usecase/
│   ├── afalgun/
│   ├── irdata/
│   ├── tikoma/
│   ├── freeservice/
│   └── withyou/
└── infrastructure/
    ├── persistence/
    │   ├── entity/
    │   └── repository/
    └── web/
        └── controller/
```

### 3. Configuration Integration
- Extend existing [`UseCaseConfig`](src/main/java/io/github/amenski/digafmedia/infrastructure/config/UseCaseConfig.java)
- Update [`DomainRulesConfig`](src/main/java/io/github/amenski/digafmedia/infrastructure/config/DomainRulesConfig.java) for new validators

## Testing Strategy

### 1. Unit Tests (Immediate Priority)
```java
// File: src/test/java/io/github/amenski/digafmedia/usecase/afalgun/CreateAfalgunPostUseCaseTest.java
package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.usecase.port.AfalgunRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAfalgunPostUseCaseTest {
    
    @Mock
    private AfalgunRepository afalgunRepository;
    
    @InjectMocks
    private CreateAfalgunPostUseCase createAfalgunPostUseCase;
    
    @Test
    void shouldCreateAfalgunPostWithTimestamp() {
        // Given
        AfalgunPost input = new AfalgunPost(
            null, "John Doe", 25, "Addis Ababa", 
            "Jane Smith", "+251911223344", "jane@example.com",
            "Last seen in Bole area", AfalgunStatus.ACTIVE, null, null
        );
        
        AfalgunPost expected = new AfalgunPost(
            null, "John Doe", 25, "Addis Ababa", 
            "Jane Smith", "+251911223344", "jane@example.com",
            "Last seen in Bole area", AfalgunStatus.ACTIVE, 
            OffsetDateTime.now(), null
        );
        
        when(afalgunRepository.save(any())).thenReturn(expected);
        
        // When
        AfalgunPost result = createAfalgunPostUseCase.invoke(input);
        
        // Then
        verify(afalgunRepository).save(any());
        // Assertions...
    }
}
```

## Deployment Considerations

### 1. Database Migration Strategy
- Enable Liquibase in [`application.yaml`](src/main/resources/application.yaml)
- Create sequential migration files (0003, 0004, etc.)
- Test migrations on development database first

### 2. Security Implementation
```java
// File: src/main/java/io/github/amenski/digafmedia/infrastructure/web/filter/AdminApiKeyFilter.java
@Component
public class AdminApiKeyFilter implements Filter {
    
    @Value("${app.admin.api-key}")
    private String validApiKey;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        if (isAdminEndpoint(httpRequest)) {
            String apiKey = httpRequest.getHeader("X-API-Key");
            if (!validApiKey.equals(apiKey)) {
                sendError(response, 401, "Invalid API key");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
```

## Success Metrics & Validation

### Week 1 Completion Criteria
- [ ] All five database tables created and tested
- [ ] Domain models implemented for all sections
- [ ] Repository ports defined
- [ ] Basic unit tests passing

### Week 2 Completion Criteria  
- [ ] JPA entities and repository implementations complete
- [ ] Use cases implemented for basic CRUD operations
- [ ] Integration tests passing
- [ ] Configuration properly set up

### Week 3 Completion Criteria
- [ ] REST controllers implemented for all sections
- [ ] API endpoints tested and documented
- [ ] Basic validation in place
- [ ] Mobile-optimized responses implemented

## Risk Mitigation

### Technical Risks
1. **Database Schema Changes**: Use Liquibase for controlled migrations
2. **Performance Issues**: Implement proper indexing from start
3. **Integration Complexity**: Follow existing patterns in codebase

### Project Risks  
1. **Scope Creep**: Focus on core functionality for each section first
2. **Timeline Pressure**: Prioritize Afalgun and Irdata sections first
3. **Quality Assurance**: Implement comprehensive testing from beginning

## Next Steps After Foundation

Once the foundation is complete (Week 3), proceed with:
1. Advanced search and filtering capabilities
2. Image upload and management
3. Enhanced security features
4. Mobile-specific optimizations
5. Admin moderation features

This implementation plan provides a clear, actionable path forward that leverages your existing Clean Architecture foundation while delivering the NGO platform functionality efficiently and maintainably.