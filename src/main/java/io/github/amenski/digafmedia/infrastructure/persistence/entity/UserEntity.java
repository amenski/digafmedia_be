package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.user.User;
import io.github.amenski.digafmedia.domain.user.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean active = true;

    public UserEntity() {}

    private UserEntity(Long id, String username, String email, String passwordHash, 
                      UserRole role, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        if (id != null) {
            setId(id);
        }
        if (createdAt != null) {
            setCreatedAt(java.time.OffsetDateTime.of(createdAt, java.time.ZoneOffset.UTC));
        }
        if (updatedAt != null) {
            setModifiedAt(java.time.OffsetDateTime.of(updatedAt, java.time.ZoneOffset.UTC));
        }
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPasswordHash(),
            user.getRole(),
            user.isActive(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public User toDomain() {
        LocalDateTime createdAt = getCreatedAt() != null ? 
            getCreatedAt().toLocalDateTime() : null;
        LocalDateTime updatedAt = getModifiedAt() != null ? 
            getModifiedAt().toLocalDateTime() : null;
        return User.of(
            getId(),
            username,
            email,
            passwordHash,
            role,
            active,
            createdAt,
            updatedAt
        );
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}