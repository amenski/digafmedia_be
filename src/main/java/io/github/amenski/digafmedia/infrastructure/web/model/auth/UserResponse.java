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

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Location", example = "New York, USA")
    private String location;

    // Default constructor for JSON deserialization
    public UserResponse() {}

    public UserResponse(Long id, String username, String email, UserRole role, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    public UserResponse(Long id, String username, String email, UserRole role, boolean active,
                       String firstName, String lastName, String phoneNumber, String location) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
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

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}