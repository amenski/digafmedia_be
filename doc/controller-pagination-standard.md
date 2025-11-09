# Controller Standard

This document outlines the standard pattern for implementing REST controllers in the DigafMedia application.

## Overview

The standard follows Clean Architecture principles with clear separation between web layer, use cases, and domain models. This pattern ensures consistent API design, proper validation, and maintainable code.

## Core Controller Structure

### Base Template

```java
@RestController
@RequestMapping("/v1/{resource}")
@Transactional(readOnly = true)
public class {Resource}Controller {

    private final {CreateResource}UseCase create{Resource}UseCase;
    private final {UpdateResource}UseCase update{Resource}UseCase;
    private final {GetResource}UseCase get{Resource}UseCase;
    // ... other use cases
    private final {Resource}WebMapper {resource}Mapper;

    public {Resource}Controller({CreateResource}UseCase create{Resource}UseCase,
                              {UpdateResource}UseCase update{Resource}UseCase,
                              {GetResource}UseCase get{Resource}UseCase,
                              {Resource}WebMapper {resource}Mapper) {
        // constructor injection
    }

    // Controller methods follow...
}
```

### Key Annotations

- **`@RestController`**: Marks as REST controller
- **`@RequestMapping`**: Base path with versioning (`/v1/`)
- **`@Transactional(readOnly = true)`**: Default read-only, override for mutations
- **`@PreAuthorize`**: Role-based security
- **`@Operation` & `@ApiResponse`**: OpenAPI documentation

## Request/Response Patterns

### 1. Pagination Framework

#### PaginatedResponse
```java
@Schema(description = "Paginated response")
public class PaginatedResponse<T> {
    private int page;           // 0-based page number
    private int size;           // page size
    private long total;         // total elements
    private int totalPages;     // total pages
    private List<T> items;      // page content
    
    public PaginatedResponse(List<T> items, int page, int size, long total, int totalPages) {
        // constructor
    }
}
```

#### PageableRequest
```java
@Schema(description = "Pagination request parameters")
public class PageableRequest {
    private int page = 0;                    // @Min(0)
    private int size = 20;                   // @Min(1) @Max(100)
    private String sortBy;
    private String sortDirection = "DESC";
}
```

### 2. Standard CRUD Operations

#### Create Operation
```java
@PostMapping
@Operation(summary = "Create {resource}")
@PreAuthorize("isAuthenticated()")
@Transactional
public ResponseEntity<{Resource}Response> create{Resource}(
        @Validated(Create.class) @RequestBody Create{Resource}Request request,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
    
    Create{Resource}Command command = {resource}Mapper.create(request);
    {Resource} saved{Resource} = create{Resource}UseCase.execute(command, new CurrentUserAdapter(userPrincipal));
    return ResponseEntity.status(HttpStatus.CREATED).body({Resource}Response.from{Resource}(saved{Resource}));
}
```

#### Update Operation
```java
@PutMapping("/{id}")
@Operation(summary = "Update {resource}")
@PreAuthorize("isAuthenticated()")
@Transactional
public ResponseEntity<{Resource}Response> update{Resource}(
        @PathVariable UUID id,
        @Validated(Update.class) @RequestBody Update{Resource}Request request,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
    
    Update{Resource}Command command = {resource}Mapper.update(request);
    {Resource} updated{Resource} = update{Resource}UseCase.execute(id, command, new CurrentUserAdapter(userPrincipal));
    return ResponseEntity.ok({Resource}Response.from{Resource}(updated{Resource}));
}
```

#### Get by ID
```java
@GetMapping("/{id}")
@Operation(summary = "Get {resource}")
public ResponseEntity<{Resource}Response> get{Resource}ById(@PathVariable UUID id) {
    {Resource} {resource} = get{Resource}UseCase.execute(id);
    return ResponseEntity.ok({Resource}Response.from{Resource}({resource}));
}
```

#### Delete Operation
```java
@DeleteMapping("/{id}")
@Operation(summary = "Delete {resource}")
@PreAuthorize("isAuthenticated()")
@Transactional
public ResponseEntity<Void> delete{Resource}(@PathVariable UUID id,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
    delete{Resource}UseCase.execute(id, new CurrentUserAdapter(userPrincipal));
    return ResponseEntity.noContent().build();
}
```

### 3. Search & List Operations

#### Search Request
```java
@Schema(description = "{Resource} search request")
public class {Resource}SearchRequest extends PageableRequest {
    private String query;                    // full-text search
    private String categoryId;              // @UUID validation
    private String location;
    private BigDecimal minPrice;            // @DecimalMin("0")
    private BigDecimal maxPrice;
    private Boolean featured;
    private Map<String, Object> filters;    // dynamic filtering
    // ... other search criteria
}
```

#### Search Endpoint
```java
@GetMapping
@Operation(summary = "List {resources}", description = "Search and filter {resources} with pagination")
public ResponseEntity<PaginatedResponse<{Resource}Response>> get{Resources}(
        @Validated({Search.class, Default.class}) {Resource}SearchRequest searchRequest) {

    Search{Resources}Command command = {resource}SearchMapper.from(searchRequest);
    var searchResult = search{Resources}UseCase.execute(command);

    List<{Resource}Response> {resource}Responses = searchResult.getContent().stream()
            .map({Resource}Response::from{Resource})
            .toList();

    var response = new PaginatedResponse<>(
            {resource}Responses,
            searchResult.getPage(),
            searchResult.getSize(),
            searchResult.getTotalElements(),
            searchResult.getTotalPages()
    );
    return ResponseEntity.ok(response);
}
```

## Request/Response Models

### Response Model Pattern
```java
@Schema(description = "{Resource} response model")
public class {Resource}Response {
    private UUID id;
    private String title;
    private String description;
    // ... other fields
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Static factory method for domain-to-web mapping
    public static {Resource}Response from{Resource}({Resource} {resource}) {
        if ({resource} == null) return null;
        
        {Resource}Response response = new {Resource}Response();
        response.setId({resource}.getId());
        response.setTitle({resource}.getTitle());
        // ... map other fields
        return response;
    }
    
    // Getters and setters...
}
```

### Create Request Pattern
```java
@Schema(description = "Request to create a new {resource}")
public class Create{Resource}Request {
    @NotBlank(message = "Title is required", groups = Create.class)
    @Size(min = 5, max = 200, message = "Title must be between 10 and 200 characters", groups = Create.class)
    private String title;

    @NotBlank(message = "Description is required", groups = Create.class)
    @Size(min = 30, max = 5000, message = "Description must be between 30 and 5000 characters", groups = Create.class)
    private String description;

    @NotNull(message = "Price is required", groups = Create.class)
    @DecimalMin(value = "0", inclusive = true, message = "Price must be greater than or equal to 0", groups = Create.class)
    private BigDecimal price;

    // ... other required fields with validation
}
```

### Update Request Pattern
```java
@Schema(description = "Request to update a {resource}")
public class Update{Resource}Request {
    @Size(min = 5, max = 200, message = "Title must be between 10 and 200 characters", groups = Update.class)
    private String title;

    @Size(min = 30, max = 5000, message = "Description must be between 30 and 5000 characters", groups = Update.class)
    private String description;

    @DecimalMin(value = "0", inclusive = true, message = "Price must be greater than or equal to 0", groups = Update.class)
    private BigDecimal price;

    // ... all fields optional for partial updates
}
```

## Validation Patterns

### Validation Groups
```java
public class ValidationGroups {
    public interface Create {}
    public interface Update {}
    public interface Search {}
}
```

### Custom Validators
```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UuidValidator.class)
public @interface UUID {
    String message() default "Must be a valid UUID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

## Security Patterns

### Authentication & Authorization
```java
// User authentication required
@PreAuthorize("isAuthenticated()")

// Admin role required  
@PreAuthorize("hasRole('ADMIN')")

// Current user context
@AuthenticationPrincipal UserPrincipal userPrincipal
```

### Current User Adapter
```java
CurrentUserAdapter currentUser = new CurrentUserAdapter(userPrincipal);
```

## Error Handling

### Standard HTTP Status Codes
- **200**: Success
- **201**: Created
- **400**: Validation errors
- **401**: Unauthorized
- **403**: Forbidden
- **404**: Not found
- **409**: Conflict (business rule violation)
- **500**: Internal server error

### Response Format
```json
{
  "timestamp": "2023-01-01T00:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/v1/resources"
}
```

## Testing Guidelines

### Controller Test Structure
```java
@WebMvcTest({Resource}Controller.class)
@Import({TestConfig.class})
class {Resource}ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Create{Resource}UseCase create{Resource}UseCase;

    @Test
    void create{Resource}_validRequest_returnsCreated() throws Exception {
        // Test implementation
    }
}
```

### Test Coverage
- Validation constraints
- Security rules
- HTTP status codes
- Response structure
- Error scenarios

## Implementation Checklist

- [ ] Controller follows standard structure with proper annotations
- [ ] Request/response models implement validation groups
- [ ] Pagination implemented using `PaginatedResponse` and `PageableRequest`
- [ ] Proper security annotations applied
- [ ] OpenAPI documentation complete
- [ ] Error handling consistent with standards
- [ ] Unit tests cover all endpoints
- [ ] Integration tests validate end-to-end flow
- [ ] Validation groups properly separated (Create/Update/Search)

## Example Implementation

See the existing controllers for reference:
- [`AfalgunController`](../src/main/java/io/github/amenski/digafmedia/infrastructure/web/controller/AfalgunController.java)
- [`CommentController`](../src/main/java/io/github/amenski/digafmedia/infrastructure/web/controller/CommentController.java)
- [`FreeServiceController`](../src/main/java/io/github/amenski/digafmedia/infrastructure/web/controller/FreeServiceController.java)

This standard ensures consistency across all REST endpoints in the DigafMedia application while maintaining Clean Architecture principles and best practices.