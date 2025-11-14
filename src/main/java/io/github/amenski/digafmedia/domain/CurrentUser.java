package io.github.amenski.digafmedia.domain;


/**
 * Domain interface for representing the current authenticated user.
 * This abstraction allows use cases to remain decoupled from Spring Security.
 */
public interface CurrentUser {
    /**
     * Gets the user ID.
     *
     * @return The user ID
     */
    Long id();

    /**
     * Gets the user's username or login identifier for auditing.
     *
     * @return The username string
     */
    String username();

    /**
     * Checks if the user has a specific role.
     *
     * @param role The role to check
     * @return True if the user has the role, false otherwise
     */
    boolean hasRole(String role);
}
