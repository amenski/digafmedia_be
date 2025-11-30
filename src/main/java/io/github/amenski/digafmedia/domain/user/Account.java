package io.github.amenski.digafmedia.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Account domain model representing authentication and authorization data.
 * Implements Spring Security UserDetails for seamless integration with Spring Security.
 * This entity contains only authentication-related data and follows immutable patterns.
 */
public class Account implements UserDetails {
    private final Long id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final UserRole role;
    private final boolean active;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    private Account(Long id, String username, String email, String passwordHash,
                   UserRole role, boolean active, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method for creating Account instances
     */
    public static Account of(Long id, String username, String email, String passwordHash,
                            UserRole role, boolean active, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Account(id, username, email, passwordHash, role, active, createdAt, updatedAt);
    }

    /**
     * Builder for creating Account instances
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String passwordHash;
        private UserRole role;
        private boolean active = true;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Account build() {
            Objects.requireNonNull(username, "username cannot be null");
            Objects.requireNonNull(email, "email cannot be null");
            Objects.requireNonNull(passwordHash, "passwordHash cannot be null");
            Objects.requireNonNull(role, "role cannot be null");

            OffsetDateTime now = OffsetDateTime.now();
            if (createdAt == null) {
                createdAt = now;
            }
            if (updatedAt == null) {
                updatedAt = now;
            }
            return new Account(id, username, email, passwordHash, role, active, createdAt, updatedAt);
        }
    }

    /**
     * Update the account with new timestamp
     */
    public Account withUpdatedAt() {
        return new Account(id, username, email, passwordHash, role, active, createdAt, OffsetDateTime.now());
    }

    /**
     * Deactivate the account
     */
    public Account deactivate() {
        return new Account(id, username, email, passwordHash, role, false, createdAt, OffsetDateTime.now());
    }

    /**
     * Activate the account
     */
    public Account activate() {
        return new Account(id, username, email, passwordHash, role, true, createdAt, OffsetDateTime.now());
    }

    /**
     * Change the account role
     */
    public Account changeRole(UserRole newRole) {
        return new Account(id, username, email, passwordHash, newRole, active, createdAt, OffsetDateTime.now());
    }

    /**
     * Change the account password
     */
    public Account changePassword(String newPasswordHash) {
        return new Account(id, username, email, newPasswordHash, role, active, createdAt, OffsetDateTime.now());
    }

    // Spring Security UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public boolean isActive() { return active; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }

    // Business methods
    public boolean hasRole(UserRole requiredRole) {
        return this.role.hasPermission(requiredRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && 
               Objects.equals(username, account.username) && 
               Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }
}