package io.github.amenski.digafmedia.domain.user;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    ADMIN(Set.of("ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER")),
    MODERATOR(Set.of("ROLE_MODERATOR", "ROLE_USER")),
    USER(Set.of("ROLE_USER"));

    private final Set<String> permissions;

    UserRole(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(UserRole requiredRole) {
        return this.permissions.containsAll(requiredRole.getPermissions());
    }

    public boolean hasAnyPermission(String... permissionNames) {
        return Arrays.stream(permissionNames)
                .anyMatch(permissions::contains);
    }

    public boolean hasAllPermissions(String... permissionNames) {
        return Arrays.stream(permissionNames)
                .allMatch(permissions::contains);
    }

    public static UserRole fromString(String role) {
        if (role == null) {
            return USER;
        }
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return USER;
        }
    }

    public static Set<UserRole> getAllRoles() {
        return Arrays.stream(UserRole.values()).collect(Collectors.toSet());
    }
}