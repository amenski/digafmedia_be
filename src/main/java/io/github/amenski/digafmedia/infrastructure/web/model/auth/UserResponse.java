package io.github.amenski.digafmedia.infrastructure.web.model.auth;

import io.github.amenski.digafmedia.domain.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User information response")
public class UserResponse {
    
    @Schema(description = "User ID", example = "123")
    private Long id;

    @Schema(description = "Username", example = "john_doe")
    private String username;

    @Schema(description = "Email address", example = "john@example.com")
    private String email;

    @Schema(description = "User role", example = "USER")
    private UserRole role;

    @Schema(description = "Whether the user is active", example = "true")
    private boolean active;

    // Default constructor for JSON deserialization
    public UserResponse() {}

    public UserResponse(Long id, String username, String email, UserRole role, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}