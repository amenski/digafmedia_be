package io.github.amenski.digafmedia.domain.user;


import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private final Long id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final UserRole role;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private User(Long id, String username, String email, String passwordHash, 
                UserRole role, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(String username, String email, String passwordHash, UserRole role) {
        return new User(null, username, email, passwordHash, role, true, 
                       LocalDateTime.now(), LocalDateTime.now());
    }

    public static User of(Long id, String username, String email, String passwordHash, 
                         UserRole role, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(id, username, email, passwordHash, role, active, createdAt, updatedAt);
    }

    public User withUpdatedAt() {
        return new User(id, username, email, passwordHash, role, active, createdAt, LocalDateTime.now());
    }

    public User deactivate() {
        return new User(id, username, email, passwordHash, role, false, createdAt, LocalDateTime.now());
    }

    public User activate() {
        return new User(id, username, email, passwordHash, role, true, createdAt, LocalDateTime.now());
    }

    public User changeRole(UserRole newRole) {
        return new User(id, username, email, passwordHash, newRole, active, createdAt, LocalDateTime.now());
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public boolean hasRole(UserRole requiredRole) {
        return this.role.hasPermission(requiredRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && 
               Objects.equals(username, user.username) && 
               Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }
}