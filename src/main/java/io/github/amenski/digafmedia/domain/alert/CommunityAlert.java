package io.github.amenski.digafmedia.domain.alert;

import java.time.OffsetDateTime;
import java.util.Objects;

public record CommunityAlert(
    Long id,
    String title,
    String message,
    String contactName,
    String contactPhone,
    AlertUrgency urgency,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    /**
     * Factory method to create a new CommunityAlert from a command.
     * Encapsulates the business rule that new alerts should have a creation timestamp.
     */
    public static CommunityAlert fromCommand(CreateCommunityAlertCommand command, Long createdBy) {
        Objects.requireNonNull(command, "Command cannot be null");
        
        return new CommunityAlert(
                null,
                command.title(),
                command.message(),
                command.contactName(),
                command.contactPhone(),
                command.urgency(),
                OffsetDateTime.now(),
                null,
                createdBy
        );
    }

    /**
     * Domain method to update the status of an existing alert.
     * Encapsulates the business rule that status updates should update the modifiedAt timestamp.
     */
    public CommunityAlert updateUrgency(AlertUrgency newUrgency) {
        Objects.requireNonNull(newUrgency, "Urgency cannot be null");
        
        if (this.urgency() == newUrgency) {
            return this; // No change needed
        }
        
        return new CommunityAlert(
                this.id(),
                this.title(),
                this.message(),
                this.contactName(),
                this.contactPhone(),
                newUrgency,
                this.createdAt(),
                OffsetDateTime.now(),
                this.createdBy()
        );
    }

    /**
     * Business rule: Check if alert is high urgency
     */
    public boolean isHighUrgency() {
        return this.urgency() == AlertUrgency.HIGH;
    }

    /**
     * Business rule: Check if alert is medium urgency
     */
    public boolean isMediumUrgency() {
        return this.urgency() == AlertUrgency.MEDIUM;
    }

    /**
     * Business rule: Check if alert is low urgency
     */
    public boolean isLowUrgency() {
        return this.urgency() == AlertUrgency.LOW;
    }

    /**
     * Business rule: Validate if the alert has required information
     */
    public boolean hasValidInformation() {
        return title() != null && !title().trim().isEmpty() &&
               message() != null && !message().trim().isEmpty();
    }

    /**
     * Business rule: Get a summary of the alert for display purposes
     */
    public String getSummary() {
        return String.format("%s (%s) - %s",
            title(),
            urgency() != null ? urgency().toString() : "UNKNOWN",
            message() != null ? message().substring(0, Math.min(message().length(), 50)) + "..." : "");
    }
}
