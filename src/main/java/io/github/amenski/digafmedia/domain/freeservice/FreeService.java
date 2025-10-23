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
) {}
