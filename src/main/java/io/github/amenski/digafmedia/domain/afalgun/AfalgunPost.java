package io.github.amenski.digafmedia.domain.afalgun;

import java.time.OffsetDateTime;
import java.util.Objects;

public record AfalgunPost(
    Long id,
    String missingPersonName,
    Integer age,
    String lastSeenLocation,
    String contactName,
    String contactPhone,
    String contactEmail,
    String description,
    AfalgunStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt
) {
    /**
     * Factory method to create a new AfalgunPost from a command.
     * Encapsulates the business rule that new posts should have a creation timestamp.
     */
    public static AfalgunPost fromCommand(CreateAfalgunPostCommand command) {
        Objects.requireNonNull(command, "Command cannot be null");
        
        return new AfalgunPost(
                null,
                command.missingPersonName(),
                command.age(),
                command.lastSeenLocation(),
                command.contactName(),
                command.contactPhone(),
                command.contactEmail(),
                command.description(),
                command.status(),
                OffsetDateTime.now(),
                null
        );
    }

    /**
     * Domain method to update the status of an existing post.
     * Encapsulates the business rule that status updates should update the modifiedAt timestamp.
     */
    public AfalgunPost updateStatus(AfalgunStatus newStatus) {
        Objects.requireNonNull(newStatus, "Status cannot be null");
        
        if (this.status() == newStatus) {
            return this; // No change needed
        }
        
        return new AfalgunPost(
                this.id(),
                this.missingPersonName(),
                this.age(),
                this.lastSeenLocation(),
                this.contactName(),
                this.contactPhone(),
                this.contactEmail(),
                this.description(),
                newStatus,
                this.createdAt(),
                OffsetDateTime.now()
        );
    }

    /**
     * Business rule: A post is considered active if its status is ACTIVE
     */
    public boolean isActive() {
        return this.status() == AfalgunStatus.ACTIVE;
    }

    /**
     * Business rule: A post is considered resolved if its status is RESOLVED
     */
    public boolean isResolved() {
        return this.status() == AfalgunStatus.RESOLVED;
    }

    /**
     * Business rule: Validate if the post has required contact information
     */
    public boolean hasValidContactInfo() {
        return contactName() != null && !contactName().trim().isEmpty() &&
               contactPhone() != null && !contactPhone().trim().isEmpty();
    }

    /**
     * Business rule: Get a summary of the post for display purposes
     */
    public String getSummary() {
        return String.format("%s, %d years - Last seen: %s",
            missingPersonName(),
            age() != null ? age() : 0,
            lastSeenLocation());
    }
}