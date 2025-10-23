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
) {}