# NGO Platform Security & Authentication Strategy

## Security Philosophy
- **Minimal Barrier**: Easy public submissions without registration
- **Essential Protection**: Basic security for critical operations
- **Mobile-First**: Optimized for low-bandwidth mobile access
- **Cost-Effective**: No complex user management systems

## Authentication Strategy

### Public Access (No Authentication Required)
- All GET endpoints (reading content)
- POST endpoints for submissions (afalgun, irdata, tikoma, with-you)
- No user registration required for basic usage

### Admin Access (Simple API Key Authentication)
- Protected operations: content moderation, service management
- Simple API key in request header for admin endpoints
- No complex user session management

## Implementation Plan

### 1. Public Submission Security

#### Rate Limiting
```java
@Bean
public RateLimitingFilter rateLimitingFilter() {
    return new RateLimitingFilter(10, 60); // 10 requests per minute
}
```

#### Input Validation
```java
public class SubmissionValidator {
    public void validatePhoneNumber(String phone) {
        // Ethiopian phone number format: +251XXXXXXXXX
        if (!phone.matches("^\\+251[1-9]\\d{8}$")) {
            throw new ValidationException("Invalid Ethiopian phone number format");
        }
    }
    
    public void sanitizeContent(String content) {
        // Basic HTML escaping and profanity filtering
        return HtmlUtils.htmlEscape(content);
    }
}
```

### 2. Admin Authentication

#### Simple API Key Approach
```java
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

#### Admin Endpoints Protection
- `PUT /v1/afalgun/posts/{id}/status` - Update missing person status
- `PUT /v1/irdata/posts/{id}/status` - Update assistance request status  
- `PUT /v1/irdata/posts/{id}/amount` - Update donation amount
- `POST /v1/free-services` - Add new free service
- `PUT /v1/free-services/{id}` - Update service information
- `PUT /v1/with-you/testimonials/{id}/approve` - Approve testimonials

### 3. Data Security Measures

#### Sensitive Data Handling
```java
public class DataSecurity {
    // Mask bank account numbers in responses
    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) return "***";
        return "***" + accountNumber.substring(accountNumber.length() - 4);
    }
    
    // Validate and sanitize file uploads
    public static void validateImageUpload(MultipartFile file) {
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            throw new ValidationException("File too large");
        }
        // Check file type
        String contentType = file.getContentType();
        if (!Arrays.asList("image/jpeg", "image/png").contains(contentType)) {
            throw new ValidationException("Invalid file type");
        }
    }
}
```

## Security Headers Configuration

### Spring Security Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // API doesn't need CSRF
            .headers(headers -> headers
                .contentSecurityPolicy("default-src 'self'")
                .frameOptions().deny()
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/v1/admin/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());
        
        return http.build();
    }
}
```

## Mobile-Friendly Security Considerations

### Optimized for Low Bandwidth
- Minimal security overhead in requests
- Compressed responses
- Efficient validation on client side when possible

### Offline Capability Considerations
- Local data caching strategies
- Conflict resolution for offline submissions
- Batch upload capabilities

## Environment Configuration

### application.yaml Security Settings
```yaml
app:
  admin:
    api-key: ${ADMIN_API_KEY:default-key-change-in-production}
    
  security:
    rate-limit:
      requests-per-minute: 10
    validation:
      max-file-size: 5MB
      allowed-image-types: image/jpeg,image/png
```

### Production Security Checklist
- [ ] Change default admin API key
- [ ] Enable HTTPS in production
- [ ] Configure proper CORS for mobile apps
- [ ] Set up monitoring for abuse detection
- [ ] Regular security updates for dependencies

## Abuse Prevention

### Basic Spam Protection
```java
@Component
public class SpamProtectionService {
    
    private final Map<String, Integer> submissionCounts = new ConcurrentHashMap<>();
    
    public boolean isAllowed(String ipAddress) {
        int count = submissionCounts.getOrDefault(ipAddress, 0);
        if (count >= 5) { // Max 5 submissions per hour per IP
            return false;
        }
        submissionCounts.put(ipAddress, count + 1);
        return true;
    }
    
    @Scheduled(fixedRate = 3600000) // Reset every hour
    public void resetCounts() {
        submissionCounts.clear();
    }
}
```

### Content Moderation
```java
@Service
public class ContentModerationService {
    
    public boolean requiresManualReview(String content) {
        // Basic keyword-based flagging
        List<String> flaggedWords = Arrays.asList("urgent", "emergency", "help");
        return flaggedWords.stream().anyMatch(content.toLowerCase()::contains);
    }
    
    public void validateContentSafety(String content) {
        if (content.length() > 10000) {
            throw new ValidationException("Content too long");
        }
        // Additional safety checks
    }
}
```

## Implementation Priority

### Phase 1 (Essential Security)
1. Input validation and sanitization
2. Basic rate limiting
3. Admin API key protection
4. File upload security

### Phase 2 (Enhanced Protection)
1. Advanced spam detection
2. Content moderation workflow
3. Security headers
4. Monitoring and logging

### Phase 3 (Advanced Features)
1. Geographic rate limiting
2. Behavioral analysis
3. Advanced content filtering
4. Audit trails

This security strategy balances accessibility for community members with essential protection for the platform, focusing on the most critical risks while maintaining the open nature required for community engagement.