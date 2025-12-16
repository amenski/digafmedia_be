package io.github.amenski.digafmedia.domain.resource;

import java.time.OffsetDateTime;
import java.util.Objects;

public record CommunityResource(
    Long id,
    String serviceName,
    String providerName,
    String description,
    String location,
    String contactPhone,
    String contactEmail,
    String category,
    String hoursOfOperation,
    Boolean isActive,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    /**
     * Factory method to create a new CommunityResource from a command.
     * Encapsulates the business rule that new resources should have a creation timestamp.
     */
    public static CommunityResource fromCommand(CreateCommunityResourceCommand command, Long createdBy) {
        Objects.requireNonNull(command, "Command cannot be null");
        
        return new CommunityResource(
                null,
                command.serviceName(),
                command.providerName(),
                command.description(),
                command.location(),
                command.contactPhone(),
                command.contactEmail(),
                command.category(),
                command.hoursOfOperation(),
                Boolean.TRUE, // Default to active
                OffsetDateTime.now(),
                null,
                createdBy
        );
    }

    /**
     * Domain method to update the status of an existing resource.
     * Encapsulates the business rule that status updates should update the modifiedAt timestamp.
     */
    public CommunityResource updateStatus(Boolean newStatus) {
        Objects.requireNonNull(newStatus, "Status cannot be null");

        if (Objects.equals(this.isActive(), newStatus)) {
            return this; // No change needed
        }

        return new CommunityResource(
                this.id(),
                this.serviceName(),
                this.providerName(),
                this.description(),
                this.location(),
                this.contactPhone(),
                this.contactEmail(),
                this.category(),
                this.hoursOfOperation(),
                newStatus,
                this.createdAt(),
                OffsetDateTime.now(),
                this.createdBy()
        );
    }

    /**
     * Business rule: Check if resource is active
     */
    public boolean isActiveForDisplay() {
        return this.isActive() != null && this.isActive();
    }

    /**
     * Business rule: Check if resource is inactive
     */
    public boolean isInactiveForDisplay() {
        return this.isActive() == null || !this.isActive();
    }

    /**
     * Business rule: Validate if the resource has required information
     */
    public boolean hasValidInformation() {
        return serviceName() != null && !serviceName().trim().isEmpty() &&
               providerName() != null && !providerName().trim().isEmpty();
    }

    /**
     * Business rule: Get a summary of the resource for display purposes
     */
    public String getSummary() {
        return String.format("%s by %s - %s",
            serviceName(),
            providerName(),
            category() != null ? category() : "General");
    }
}
