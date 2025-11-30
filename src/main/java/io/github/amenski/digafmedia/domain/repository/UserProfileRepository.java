package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.user.UserProfile;

import java.util.Optional;

/**
 * Repository interface for UserProfile domain model.
 * Defines the contract for user profile persistence operations.
 */
public interface UserProfileRepository {
    
    /**
     * Save a user profile (create or update)
     */
    UserProfile save(UserProfile userProfile);
    
    /**
     * Find user profile by ID
     */
    Optional<UserProfile> findById(Long id);
    
    /**
     * Find user profile by account ID
     */
    Optional<UserProfile> findByAccountId(Long accountId);
    
    /**
     * Delete user profile by ID
     */
    void deleteById(Long id);
    
    /**
     * Delete user profile by account ID
     */
    void deleteByAccountId(Long accountId);
    
    /**
     * Check if user profile exists by account ID
     */
    boolean existsByAccountId(Long accountId);
}