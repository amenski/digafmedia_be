package io.github.amenski.digafmedia.domain.tikoma;

import java.time.OffsetDateTime;

public record TikomaAlert(
    Long id,
    String title,
    String message,
    String contactName,
    String contactPhone,
    TikomaUrgency urgency,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt
) {}
