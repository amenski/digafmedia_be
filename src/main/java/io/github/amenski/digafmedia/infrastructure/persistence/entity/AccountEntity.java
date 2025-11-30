package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.user.Account;
import io.github.amenski.digafmedia.domain.user.UserRole;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class AccountEntity extends BaseEntity {
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

    public AccountEntity() {}

    private AccountEntity(Long id, String username, String email, String passwordHash, 
                         UserRole role, boolean active, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        if (id != null) {
            setId(id);
        }
        if (createdAt != null) {
            setCreatedAt(createdAt);
        }
        if (updatedAt != null) {
            setModifiedAt(updatedAt);
        }
    }

    public static AccountEntity fromDomain(Account account) {
        return new AccountEntity(
            account.getId(),
            account.getUsername(),
            account.getEmail(),
            account.getPasswordHash(),
            account.getRole(),
            account.isActive(),
            account.getCreatedAt(),
            account.getUpdatedAt()
        );
    }

    public Account toDomain() {
        return Account.of(
            getId(),
            username,
            email,
            passwordHash,
            role,
            active,
            getCreatedAt(),
            getModifiedAt()
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