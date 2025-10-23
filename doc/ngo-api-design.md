# NGO Platform API Design & Use Cases

## API Architecture Overview

### Base Structure
- **Base Path**: `/api/v1`
- **Content Type**: `application/json`
- **Authentication**: Public submissions, Admin moderation
- **Rate Limiting**: Basic protection on POST endpoints

## Section 1: Afalgun (Lost Person Search)

### Domain Model
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

### Use Cases
1. **CreateAfalgunPostUseCase**
   - Validates required fields (missing person name, contact info)
   - Sets default status to ACTIVE
   - Records creation timestamp

2. **GetActiveAfalgunPostsUseCase**
   - Returns only ACTIVE posts
   - Supports pagination and basic filtering

3. **UpdateAfalgunPostStatusUseCase**
   - Allows status updates (FOUND, CLOSED)
   - Admin-only operation

### API Endpoints
```
GET    /afalgun/posts?status=ACTIVE&page=0&size=20
POST   /afalgun/posts
GET    /afalgun/posts/{id}
PUT    /afalgun/posts/{id}/status
```

### Request/Response Examples
```json
// POST /afalgun/posts
{
  "missingPersonName": "John Doe",
  "age": 25,
  "lastSeenLocation": "Addis Ababa, Bole area",
  "contactName": "Jane Smith",
  "contactPhone": "+251911223344",
  "contactEmail": "jane@example.com",
  "description": "Last seen wearing blue shirt and black pants"
}

// Response
{
  "id": 1,
  "missingPersonName": "John Doe",
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

## Section 2: Irdata (Crowdfunding Assistance)

### Domain Model
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

### Use Cases
1. **CreateIrdataPostUseCase**
   - Validates required fields (title, description, contact info)
   - Sets default status to ACTIVE
   - Initializes current amount to 0

2. **UpdateIrdataAmountUseCase**
   - Updates current amount (manual updates since no payment processing)
   - Can update status to FUNDED if goal reached

3. **GetActiveIrdataPostsUseCase**
   - Returns ACTIVE posts with progress information

### API Endpoints
```
GET    /irdata/posts?status=ACTIVE
POST   /irdata/posts
GET    /irdata/posts/{id}
PUT    /irdata/posts/{id}/amount
PUT    /irdata/posts/{id}/status
```

### Request/Response Examples
```json
// POST /irdata/posts
{
  "title": "Medical Assistance for Ahmed",
  "description": "Urgent medical treatment needed",
  "goalAmount": 50000.00,
  "bankName": "Commercial Bank of Ethiopia",
  "accountNumber": "100023456789",
  "accountHolder": "Ahmed Mohammed",
  "contactName": "Family Member",
  "contactPhone": "+251922334455"
}

// Response
{
  "id": 1,
  "title": "Medical Assistance for Ahmed",
  "goalAmount": 50000.00,
  "currentAmount": 0.00,
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

## Section 3: Tikoma (Community Alerts)

### Domain Model
```java
public record TikomaAlert(
    Long id,
    String title,
    String message,
    String contactName,
    String contactPhone,
    Urgency urgency,
    OffsetDateTime createdAt
) {}

public enum Urgency { LOW, MEDIUM, HIGH }
```

### Use Cases
1. **CreateTikomaAlertUseCase**
   - Validates required fields (title, message)
   - Sets default urgency to MEDIUM
   - No approval required for immediate posting

2. **GetRecentTikomaAlertsUseCase**
   - Returns alerts sorted by creation date (newest first)
   - Can filter by urgency level

### API Endpoints
```
GET    /tikoma/alerts?urgency=HIGH&limit=50
POST   /tikoma/alerts
```

### Request/Response Examples
```json
// POST /tikoma/alerts
{
  "title": "Water Shortage Alert",
  "message": "Scheduled water interruption in Bole area from 8AM-4PM",
  "contactName": "City Utility",
  "contactPhone": "+251111223344",
  "urgency": "HIGH"
}

// Response
{
  "id": 1,
  "title": "Water Shortage Alert",
  "urgency": "HIGH",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

## Section 4: Free-Service (Service Directory)

### Domain Model
```java
public record FreeService(
    Long id,
    String serviceName,
    String providerName,
    String description,
    String location,
    String contactPhone,
    String contactEmail,
    String category,
    String hoursOfOperation,
    Boolean isActive,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
```

### Use Cases
1. **CreateFreeServiceUseCase**
   - Admin-only operation
   - Validates required service information
   - Sets default active status to true

2. **SearchFreeServicesUseCase**
   - Search by category, location, or keyword
   - Returns only active services

3. **UpdateFreeServiceUseCase**
   - Admin-only operation for service updates

### API Endpoints
```
GET    /free-services?category=medical&location=addis
POST   /free-services
GET    /free-services/{id}
PUT    /free-services/{id}
GET    /free-services/categories
```

### Request/Response Examples
```json
// POST /free-services
{
  "serviceName": "Free Medical Checkup",
  "providerName": "Hope Medical Center",
  "description": "Basic health screening and consultation",
  "location": "Bole, Addis Ababa",
  "contactPhone": "+251911445566",
  "category": "medical",
  "hoursOfOperation": "Mon-Fri 8:00-17:00"
}

// Response
{
  "id": 1,
  "serviceName": "Free Medical Checkup",
  "providerName": "Hope Medical Center",
  "category": "medical",
  "isActive": true
}
```

## Section 5: With-You (Testimonials)

### Domain Model
```java
public record WithYouTestimonial(
    Long id,
    String title,
    String story,
    String authorName,
    String authorLocation,
    Boolean isApproved,
    OffsetDateTime createdAt
) {}
```

### Use Cases
1. **CreateTestimonialUseCase**
   - Public submission with auto-approval false
   - Validates required fields (title, story)

2. **ApproveTestimonialUseCase**
   - Admin-only operation
   - Sets isApproved to true

3. **GetApprovedTestimonialsUseCase**
   - Returns only approved testimonials
   - Sorted by creation date

### API Endpoints
```
GET    /with-you/testimonials
POST   /with-you/testimonials
PUT    /with-you/testimonials/{id}/approve
```

### Request/Response Examples
```json
// POST /with-you/testimonials
{
  "title": "Community Support Saved My Family",
  "story": "When we lost our home in the flood, the community came together...",
  "authorName": "Maria Samuel",
  "authorLocation": "Hawassa"
}

// Response
{
  "id": 1,
  "title": "Community Support Saved My Family",
  "isApproved": false,
  "createdAt": "2024-01-15T10:30:00Z"
}
```

## Common Use Cases & Shared Components

### Image Management
```java
public record PostImage(
    Long id,
    PostType postType,
    Long postId,
    String filePath,
    String altText,
    Integer displayOrder
) {}

public enum PostType { AFALGUN, IRDATA, TIKOMA, FREE_SERVICE, WITH_YOU }
```

### Image Use Cases
1. **UploadImageUseCase**
   - Validates image type and size
   - Stores image metadata
   - Returns file path

2. **GetPostImagesUseCase**
   - Returns all images for a specific post

### Search & Filtering
1. **SearchUseCase** (generic)
   - Supports keyword search across multiple sections
   - Location-based filtering
   - Category-based filtering for services

### Validation Rules
1. **Contact Information Validation**
   - Ethiopian phone number format validation
   - Basic email format validation
   - Required contact fields validation

2. **Content Moderation**
   - Basic profanity filter
   - Duplicate content detection
   - Spam prevention

## Error Handling & Response Standards

### Standard Error Response
```json
{
  "error": "VALIDATION_ERROR",
  "message": "Required field 'contactPhone' is missing",
  "timestamp": "2024-01-15T10:30:00Z",
  "details": {
    "field": "contactPhone",
    "constraint": "REQUIRED"
  }
}
```

### Common Error Types
- `VALIDATION_ERROR` - Input validation failed
- `NOT_FOUND` - Resource not found
- `UNAUTHORIZED` - Admin operation attempted without authorization
- `RATE_LIMITED` - Too many requests

## Next Steps for Implementation

1. **Phase 1**: Implement core domain models and basic CRUD operations
2. **Phase 2**: Add validation rules and error handling
3. **Phase 3**: Implement search and filtering capabilities
4. **Phase 4**: Add image upload support
5. **Phase 5**: Implement admin moderation features

This API design provides a solid foundation for the NGO platform while maintaining simplicity and focusing on the core functionality needed for community impact.