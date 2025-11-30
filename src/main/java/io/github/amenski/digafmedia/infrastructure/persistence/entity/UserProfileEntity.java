package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.user.UserProfile;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user_profiles", uniqueConstraints = {
    @UniqueConstraint(columnNames = "account_id")
})
public class UserProfileEntity extends BaseEntity {
    @Column(name = "account_id", nullable = false, unique = true)
    private Long accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    public UserProfileEntity() {}

    private UserProfileEntity(Long id, Long accountId, String firstName, String lastName,
                             String phoneNumber, String location, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
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

    public static UserProfileEntity fromDomain(UserProfile userProfile) {
        return new UserProfileEntity(
            userProfile.getId(),
            userProfile.getAccountId(),
            userProfile.getFirstName(),
            userProfile.getLastName(),
            userProfile.getPhoneNumber(),
            userProfile.getLocation(),
            userProfile.getCreatedAt(),
            userProfile.getUpdatedAt()
        );
    }

    public UserProfile toDomain() {
        return UserProfile.of(
            getId(),
            accountId,
            firstName,
            lastName,
            phoneNumber,
            location,
            getCreatedAt(),
            getModifiedAt()
        );
    }

    // Getters and setters
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}