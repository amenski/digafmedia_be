package io.github.amenski.digafmedia.domain.missingperson;

import java.time.OffsetDateTime;
import java.util.Objects;

public record MissingPersonReport(
    Long id,
    String missingPersonName,
    Integer age,
    String lastSeenLocation,
    String contactName,
    String contactPhone,
    String contactEmail,
    String description,
    ReportStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    /**
     * Factory method to create a new MissingPersonReport from a command.
     * Encapsulates the business rule that new reports should have a creation timestamp.
     */
    public static MissingPersonReport fromCommand(CreateMissingPersonReportCommand command, Long createdBy) {
        Objects.requireNonNull(command, "Command cannot be null");
        
        return new MissingPersonReport(
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
                null,
                createdBy
        );
    }

    /**
     * Domain method to update the status of an existing report.
     * Encapsulates the business rule that status updates should update the modifiedAt timestamp.
     */
    public MissingPersonReport updateStatus(ReportStatus newStatus) {
        Objects.requireNonNull(newStatus, "Status cannot be null");
        
        if (this.status() == newStatus) {
            return this; // No change needed
        }
        
        return new MissingPersonReport(
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
                OffsetDateTime.now(),
                this.createdBy()
        );
    }

    /**
     * Business rule: A report is considered active if its status is ACTIVE
     */
    public boolean isActive() {
        return this.status() == ReportStatus.ACTIVE;
    }

    /**
     * Business rule: A report is considered resolved if its status is RESOLVED
     */
    public boolean isResolved() {
        return this.status() == ReportStatus.RESOLVED;
    }

    /**
     * Business rule: Validate if the report has required contact information
     */
    public boolean hasValidContactInfo() {
        return contactName() != null && !contactName().trim().isEmpty() &&
               contactPhone() != null && !contactPhone().trim().isEmpty();
    }

    /**
     * Business rule: Get a summary of the report for display purposes
     */
    public String getSummary() {
        return String.format("%s, %d years - Last seen: %s",
            missingPersonName(),
            age() != null ? age() : 0,
            lastSeenLocation());
    }
}