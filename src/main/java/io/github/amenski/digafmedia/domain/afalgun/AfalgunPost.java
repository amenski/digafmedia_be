package io.github.amenski.digafmedia.domain.afalgun;

import java.time.OffsetDateTime;

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
}