package io.github.amenski.digafmedia.domain.user;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * UserProfile domain model representing user contact information.
 * This entity contains non-authentication data for user outreach.
 * Follows immutable patterns and clean architecture principles.
 */
public class UserProfile {
    private final Long id;
    private final Long accountId;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String location;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    private UserProfile(Long id, Long accountId, String firstName, String lastName,
                       String phoneNumber, String location, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method for creating UserProfile instances
     */
    public static UserProfile of(Long id, Long accountId, String firstName, String lastName,
                                 String phoneNumber, String location, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new UserProfile(id, accountId, firstName, lastName, phoneNumber,
                              location, createdAt, updatedAt);
    }

    /**
     * Builder for creating UserProfile instances
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long accountId;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String location;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
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

        public UserProfile build() {
            Objects.requireNonNull(accountId, "accountId cannot be null");
            OffsetDateTime now = OffsetDateTime.now();
            if (createdAt == null) {
                createdAt = now;
            }
            if (updatedAt == null) {
                updatedAt = now;
            }
            return new UserProfile(id, accountId, firstName, lastName, phoneNumber,
                                  location, createdAt, updatedAt);
        }
    }

    /**
     * Update the profile with new timestamp
     */
    public UserProfile withUpdatedAt() {
        return new UserProfile(id, accountId, firstName, lastName, phoneNumber,
                              location, createdAt, OffsetDateTime.now());
    }

    /**
     * Update basic profile information
     */
    public UserProfile updateBasicInfo(String firstName, String lastName, String location) {
        return new UserProfile(id, accountId, firstName, lastName, phoneNumber,
                              location, createdAt, OffsetDateTime.now());
    }

    /**
     * Update contact information
     */
    public UserProfile updateContactInfo(String phoneNumber, String location) {
        return new UserProfile(id, accountId, firstName, lastName, phoneNumber,
                              location, createdAt, OffsetDateTime.now());
    }

    /**
     * Check if profile has complete contact information
     */
    public boolean hasCompleteContactInfo() {
        return phoneNumber != null && !phoneNumber.trim().isEmpty() && 
               location != null && !location.trim().isEmpty();
    }

    /**
     * Check if profile has basic information
     */
    public boolean hasBasicInfo() {
        return firstName != null && !firstName.trim().isEmpty() && 
               lastName != null && !lastName.trim().isEmpty();
    }

    /**
     * Get full name if available
     */
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return null;
    }

    // Getters
    public Long getId() { return id; }
    public Long getAccountId() { return accountId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getLocation() { return location; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}