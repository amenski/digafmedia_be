# NGO Digital Platform System Design

## Overview
A comprehensive backend system for an NGO in Ethiopia enabling digital listing and nationwide access to critical services and information, eliminating paper-based methods.

## Core Sections

### 1. Afalgun (Lost Person Search)
**Purpose**: Search for missing persons with basic information sharing

**Data Structure**:
```sql
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
```

### 2. Irdata (Crowdfunding Assistance)
**Purpose**: Platform for posting assistance requests with bank details (no payment processing)

**Data Structure**:
```sql
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
```

### 3. Tikoma (Community Alerts)
**Purpose**: Simple community messaging platform for important alerts

**Data Structure**:
```sql
CREATE TABLE tikoma_alert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    urgency ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 4. Free-Service (Service Directory)
**Purpose**: Catalog of available free services with location and contact details

**Data Structure**:
```sql
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
```

### 5. With-You (Testimonials)
**Purpose**: Community testimonials and success stories

**Data Structure**:
```sql
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

## Media Support (Images)
```sql
CREATE TABLE post_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_type ENUM('AFALGUN', 'IRDATA', 'TIKOMA', 'FREE_SERVICE', 'WITH_YOU') NOT NULL,
    post_id BIGINT NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    display_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## Domain Models (Java Records)

### Afalgun Domain
```java
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

public enum AfalgunStatus { ACTIVE, FOUND, CLOSED }
```

### Irdata Domain
```java
public record IrdataPost(
    Long id,
    String title,
    String description,
    BigDecimal goalAmount,
    BigDecimal currentAmount,
    String bankName,
    String accountNumber,
    String accountHolder,
    String contactName,
    String contactPhone,
    String contactEmail,
    IrdataStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}

public enum IrdataStatus { ACTIVE, FUNDED, CLOSED }
```

## API Endpoints Design

### Afalgun Endpoints
```
GET    /v1/afalgun           - List all active missing person posts
POST   /v1/afalgun           - Create new missing person post
GET    /v1/afalgun/{id}      - Get specific missing person post
PUT    /v1/afalgun/{id}      - Update post status (found/closed)
```

### Irdata Endpoints
```
GET    /v1/irdata            - List all active assistance requests
POST   /v1/irdata            - Create new assistance request
GET    /v1/irdata/{id}       - Get specific assistance request
PUT    /v1/irdata/{id}       - Update assistance request (status/amount)
```

### Tikoma Endpoints
```
GET    /v1/tikoma            - List recent community alerts
POST   /v1/tikoma            - Create new community alert
```

### Free-Service Endpoints
```
GET    /v1/free-services     - List all active free services
POST   /v1/free-services     - Add new free service (admin)
GET    /v1/free-services/search - Search services by category/location
```

### With-You Endpoints
```
GET    /v1/with-you          - List approved testimonials
POST   /v1/with-you          - Submit new testimonial
PUT    /v1/with-you/{id}/approve - Approve testimonial (admin)
```

## Security & Access Control

### Public Access
- All GET endpoints are publicly accessible
- POST endpoints for submissions are publicly accessible
- No user registration required for basic submissions

### Admin Features
- Content moderation and approval
- Service management
- Statistics and reporting

### Security Considerations
- Input validation and sanitization
- Rate limiting on submission endpoints
- Basic spam protection
- No sensitive data storage

## Implementation Strategy

### Phase 1: Core Infrastructure
1. Update existing domain models and repositories
2. Implement basic CRUD operations for all sections
3. Set up database migrations
4. Basic input validation

### Phase 2: Enhanced Features
1. Search and filtering capabilities
2. Image upload support
3. Basic moderation dashboard
4. Location-based services

### Phase 3: Advanced Features
1. Mobile optimization
2. Notification system
3. Analytics and reporting
4. Multi-language support (Amharic)

## Technical Stack
- **Backend**: Spring Boot 3.x (existing)
- **Database**: MySQL (existing)
- **Architecture**: Clean Architecture (existing)
- **API Documentation**: SpringDoc OpenAPI (existing)
- **Security**: Basic input validation and rate limiting

## Key Benefits
- Eliminates paper-based information sharing
- Nationwide accessibility via mobile devices
- Cost-effective digital solution
- Real-time information updates
- Community-driven content