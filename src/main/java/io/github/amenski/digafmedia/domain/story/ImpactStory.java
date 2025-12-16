package io.github.amenski.digafmedia.domain.story;

import java.time.OffsetDateTime;
import java.util.Objects;

public record ImpactStory(
    Long id,
    String title,
    String story,
    String authorName,
    String authorLocation,
    Boolean isApproved,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    /**
     * Factory method to create a new ImpactStory from a command.
     * Encapsulates the business rule that new stories should have a creation timestamp.
     */
    public static ImpactStory fromCommand(CreateImpactStoryCommand command, Long createdBy) {
        Objects.requireNonNull(command, "Command cannot be null");
        
        return new ImpactStory(
                null,
                command.title(),
                command.story(),
                command.authorName(),
                command.authorLocation(),
                Boolean.FALSE, // Default to not approved
                OffsetDateTime.now(),
                null,
                createdBy
        );
    }

    /**
     * Domain method to update the approval status of an existing story.
     * Encapsulates the business rule that status updates should update the modifiedAt timestamp.
     */
    public ImpactStory updateApproval(Boolean newStatus) {
        Objects.requireNonNull(newStatus, "Approval status cannot be null");

        if (Objects.equals(this.isApproved(), newStatus)) {
            return this; // No change needed
        }

        return new ImpactStory(
                this.id(),
                this.title(),
                this.story(),
                this.authorName(),
                this.authorLocation(),
                newStatus,
                this.createdAt(),
                OffsetDateTime.now(),
                this.createdBy()
        );
    }

    /**
     * Business rule: Check if story is approved
     */
    public boolean isApprovedForDisplay() {
        return this.isApproved() != null && this.isApproved();
    }

    /**
     * Business rule: Check if story is not approved
     */
    public boolean isNotApprovedForDisplay() {
        return this.isApproved() == null || !this.isApproved();
    }

    /**
     * Business rule: Validate if the story has required information
     */
    public boolean hasValidInformation() {
        return title() != null && !title().trim().isEmpty() &&
               story() != null && !story().trim().isEmpty();
    }

    /**
     * Business rule: Get a summary of the story for display purposes
     */
    public String getSummary() {
        return String.format("%s by %s",
            title(),
            authorName() != null ? authorName() : "Anonymous");
    }
}
