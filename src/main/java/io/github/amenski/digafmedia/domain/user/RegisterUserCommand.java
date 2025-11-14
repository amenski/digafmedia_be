package io.github.amenski.digafmedia.domain.user;

import io.github.amenski.digafmedia.domain.ValidationResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class RegisterUserCommand {
    private final String username;
    private final String email;
    private final String password;
    private final UserRole role;

    private RegisterUserCommand(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static RegisterUserCommand create(String username, String email, String password, UserRole role) {
        return new RegisterUserCommand(username, email, password, role);
    }

    public static RegisterUserCommand createUser(String username, String email, String password) {
        return new RegisterUserCommand(username, email, password, UserRole.USER);
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();

        // Username validation
        if (StringUtils.isBlank(username)) {
            result.addError("username", "Username is required");
        } else if (username.length() < 3 || username.length() > 50) {
            result.addError("username", "Username must be between 3 and 50 characters");
        } else if (!username.matches("^[a-zA-Z0-9_]+$")) {
            result.addError("username", "Username can only contain letters, numbers, and underscores");
        }

        // Email validation
        if (StringUtils.isBlank(email)) {
            result.addError("email", "Email is required");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            result.addError("email", "Email format is invalid");
        }

        // Password validation
        if (StringUtils.isBlank(password)) {
            result.addError("password", "Password is required");
        } else if (password.length() < 8) {
            result.addError("password", "Password must be at least 8 characters long");
        } else if (!password.matches(".*[A-Z].*")) {
            result.addError("password", "Password must contain at least one uppercase letter");
        } else if (!password.matches(".*[a-z].*")) {
            result.addError("password", "Password must contain at least one lowercase letter");
        } else if (!password.matches(".*\\d.*")) {
            result.addError("password", "Password must contain at least one digit");
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUserCommand that = (RegisterUserCommand) o;
        return Objects.equals(username, that.username) && 
               Objects.equals(email, that.email) && 
               Objects.equals(password, that.password) && 
               role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, role);
    }

    @Override
    public String toString() {
        return "RegisterUserCommand{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}