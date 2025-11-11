package io.github.amenski.digafmedia.domain.freeservice;

import java.time.OffsetDateTime;

public record FreeService(
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
    OffsetDateTime modifiedAt
) {
    /**
     * Factory method to create a FreeService with default isActive if not provided.
     * This encapsulates the business rule that new services default to active.
     */
    public static FreeService withDefaults(FreeService service) {
        if (service.isActive() != null) {
            return service;
        }
        return new FreeService(
                service.id(),
                service.serviceName(),
                service.providerName(),
                service.description(),
                service.location(),
                service.contactPhone(),
                service.contactEmail(),
                service.category(),
                service.hoursOfOperation(),
                true,
                service.createdAt(),
                service.modifiedAt()
        );
    }
}
