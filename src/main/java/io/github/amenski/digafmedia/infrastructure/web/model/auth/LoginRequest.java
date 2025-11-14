package io.github.amenski.digafmedia.infrastructure.web.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request")
public class LoginRequest {
    
    @NotBlank(message = "Username is required")
    @Schema(description = "Username", example = "john_doe", required = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password", example = "Password123!", required = true)
    private String password;

    // Default constructor for JSON deserialization
    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}