# NGO Platform Mobile-Friendly API Design

## Mobile Optimization Strategy

### Core Principles for Ethiopian Mobile Users
- **Low Bandwidth Optimization**: Minimize payload size, compress responses
- **Offline Resilience**: Support for intermittent connectivity
- **Battery Efficient**: Minimize API calls and data transfer
- **Progressive Enhancement**: Basic functionality works on all devices

## API Response Standards

### Standard Response Structure
```json
{
  "data": { ... },
  "meta": {
    "timestamp": "2024-01-15T10:30:00Z",
    "version": "v1",
    "pagination": { ... }
  },
  "error": null
}
```

### Error Response Structure
```json
{
  "data": null,
  "meta": {
    "timestamp": "2024-01-15T10:30:00Z",
    "version": "v1"
  },
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Phone number is required",
    "details": {
      "field": "contactPhone",
      "constraint": "REQUIRED"
    }
  }
}
```

## Section-Specific Mobile Optimizations

### 1. Afalgun (Lost Person Search)

#### List Response (Optimized for Mobile)
```json
{
  "data": {
    "posts": [
      {
        "id": 1,
        "missingPersonName": "John Doe",
        "age": 25,
        "lastSeenLocation": "Addis Ababa, Bole",
        "status": "ACTIVE",
        "createdAt": "2024-01-15T10:30:00Z",
        "_links": {
          "self": "/v1/afalgun/posts/1",
          "images": "/v1/afalgun/posts/1/images"
        }
      }
    ]
  },
  "meta": {
    "total": 15,
    "page": 0,
    "size": 10,
    "hasNext": true
  }
}
```

#### Detail Response (Lazy Loading)
```json
{
  "data": {
    "id": 1,
    "missingPersonName": "John Doe",
    "age": 25,
    "lastSeenLocation": "Addis Ababa, Bole area",
    "contactName": "Jane Smith",
    "contactPhone": "+251911223344",
    "description": "Last seen wearing blue shirt...",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00Z",
    "images": [
      {
        "id": 1,
        "thumbnailUrl": "/images/1/thumb.jpg",
        "fullUrl": "/images/1/full.jpg",
        "altText": "Person photo"
      }
    ]
  }
}
```

### 2. Irdata (Crowdfunding Assistance)

#### List Response with Progress
```json
{
  "data": {
    "posts": [
      {
        "id": 1,
        "title": "Medical Assistance for Ahmed",
        "description": "Urgent medical treatment needed...",
        "goalAmount": 50000.00,
        "currentAmount": 12500.00,
        "progress": 25,
        "status": "ACTIVE",
        "createdAt": "2024-01-15T10:30:00Z",
        "bankDetails": {
          "bankName": "Commercial Bank of Ethiopia",
          "accountNumber": "***6789"  // Masked for list view
        }
      }
    ]
  }
}
```

#### Detail Response
```json
{
  "data": {
    "id": 1,
    "title": "Medical Assistance for Ahmed",
    "description": "Full description with details...",
    "goalAmount": 50000.00,
    "currentAmount": 12500.00,
    "progress": 25,
    "bankName": "Commercial Bank of Ethiopia",
    "accountNumber": "100023456789",  // Full details in detail view
    "accountHolder": "Ahmed Mohammed",
    "contactName": "Family Member",
    "contactPhone": "+251922334455",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-16T08:15:00Z"
  }
}
```

### 3. Tikoma (Community Alerts)

#### Optimized Alert List
```json
{
  "data": {
    "alerts": [
      {
        "id": 1,
        "title": "Water Shortage Alert",
        "message": "Scheduled water interruption...",
        "urgency": "HIGH",
        "createdAt": "2024-01-15T10:30:00Z",
        "isNew": true  // For client-side highlighting
      }
    ]
  },
  "meta": {
    "lastUpdate": "2024-01-15T10:30:00Z"
  }
}
```

### 4. Free-Service (Service Directory)

#### Search Response
```json
{
  "data": {
    "services": [
      {
        "id": 1,
        "serviceName": "Free Medical Checkup",
        "providerName": "Hope Medical Center",
        "location": "Bole, Addis Ababa",
        "category": "medical",
        "contactPhone": "+251911445566",
        "hoursOfOperation": "Mon-Fri 8:00-17:00",
        "distance": 2.5,  // In kilometers (if location provided)
        "isActive": true
      }
    ]
  },
  "meta": {
    "categories": ["medical", "education", "food", "shelter"],
    "totalByCategory": {
      "medical": 5,
      "education": 3
    }
  }
}
```

### 5. With-You (Testimonials)

#### Testimonial List
```json
{
  "data": {
    "testimonials": [
      {
        "id": 1,
        "title": "Community Support Saved My Family",
        "excerpt": "When we lost our home in the flood...",
        "authorName": "Maria Samuel",
        "authorLocation": "Hawassa",
        "createdAt": "2024-01-15T10:30:00Z",
        "readTime": 2  // Estimated reading time in minutes
      }
    ]
  }
}
```

## Mobile-Specific Features

### 1. Pagination for Low Bandwidth
```json
{
  "meta": {
    "pagination": {
      "page": 0,
      "size": 10,
      "totalElements": 47,
      "totalPages": 5,
      "hasNext": true,
      "hasPrevious": false
    }
  }
}
```

### 2. Image Optimization
```json
{
  "images": [
    {
      "id": 1,
      "thumbnails": {
        "small": "/images/1/small.jpg?size=150",    // 150x150
        "medium": "/images/1/medium.jpg?size=300",  // 300x300
        "large": "/images/1/large.jpg?size=600"     // 600x600
      },
      "original": "/images/1/original.jpg",
      "fileSize": 2048576,
      "dimensions": {"width": 1200, "height": 800}
    }
  ]
}
```

### 3. Location-Based Services
```json
{
  "services": [
    {
      "id": 1,
      "serviceName": "Free Medical Checkup",
      "location": {
        "address": "Bole, Addis Ababa",
        "coordinates": {
          "latitude": 9.0056,
          "longitude": 38.7636
        },
        "distance": 2.5,  // From user's location
        "bearing": 45     // Direction in degrees
      }
    }
  ]
}
```

## Performance Optimizations

### 1. Response Compression
```java
@Configuration
public class CompressionConfig {
    @Bean
    public FilterRegistrationBean<GzipFilter> gzipFilter() {
        FilterRegistrationBean<GzipFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new GzipFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }
}
```

### 2. Conditional Requests (ETag Support)
```java
@GetMapping("/v1/afalgun/posts/{id}")
public ResponseEntity<AfalgunPost> getPost(@PathVariable Long id, 
                                          WebRequest request) {
    AfalgunPost post = service.getPost(id);
    String eTag = computeETag(post);
    
    if (request.checkNotModified(eTag)) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
    
    return ResponseEntity.ok()
        .eTag(eTag)
        .body(post);
}
```

### 3. Field Selection (Partial Responses)
```
GET /v1/afalgun/posts?fields=id,missingPersonName,lastSeenLocation,status
```

### 4. Batch Operations
```json
// Batch request for multiple posts
POST /v1/batch
{
  "requests": [
    {"method": "GET", "url": "/v1/afalgun/posts/1"},
    {"method": "GET", "url": "/v1/irdata/posts/2"}
  ]
}
```

## Offline Support

### 1. Sync Endpoints
```
GET /v1/sync/afalgun?since=2024-01-15T00:00:00Z
```

### 2. Offline Submission Queue
```json
{
  "offlineSubmissions": [
    {
      "localId": "temp-123",
      "type": "AFALGUN",
      "data": { ... },
      "createdAt": "2024-01-15T10:30:00Z",
      "status": "PENDING"
    }
  ]
}
```

### 3. Conflict Resolution
```json
{
  "conflicts": [
    {
      "localId": "temp-123",
      "serverId": 456,
      "localData": { ... },
      "serverData": { ... },
      "resolution": "KEEP_SERVER" // or "KEEP_LOCAL", "MERGE"
    }
  ]
}
```

## Mobile-Specific Error Handling

### Network-Related Errors
```json
{
  "error": {
    "code": "NETWORK_ERROR",
    "message": "Unable to connect to server",
    "suggestions": [
      "Check your internet connection",
      "Try again in a few minutes",
      "Use offline mode if available"
    ]
  }
}
```

### Data Validation Errors
```json
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Please check your input",
    "details": [
      {
        "field": "contactPhone",
        "message": "Phone number must be in Ethiopian format (+251...)",
        "suggestion": "Example: +251911223344"
      }
    ]
  }
}
```

## Implementation Priorities

### Phase 1 (Essential Mobile Features)
1. Response compression and caching
2. Basic pagination
3. Optimized image delivery
4. Clear error messages

### Phase 2 (Enhanced Mobile Experience)
1. Field selection for partial responses
2. Location-based services
3. Offline submission support
4. Batch operations

### Phase 3 (Advanced Mobile Optimization)
1. Progressive image loading
2. Advanced caching strategies
3. Real-time updates (WebSocket/SSE)
4. Adaptive quality based on network conditions

This mobile-friendly API design ensures that the NGO platform will work effectively across the diverse range of mobile devices and network conditions found throughout Ethiopia, from urban centers with good connectivity to rural areas with limited bandwidth.