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
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    /**
     * Factory method to create a TikomaAlert with default urgency if not provided.
     * This encapsulates the business rule that new alerts default to MEDIUM urgency.
     */
    public static TikomaAlert withDefaults(TikomaAlert alert, Long createdBy) {
        if (alert.urgency() != null) {
            return alert;
        }
        return new TikomaAlert(
                alert.id(),
                alert.title(),
                alert.message(),
                alert.contactName(),
                alert.contactPhone(),
                TikomaUrgency.MEDIUM,
                alert.createdAt(),
                alert.modifiedAt(),
                createdBy
        );
    }
}
