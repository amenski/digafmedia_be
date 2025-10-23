package io.github.amenski.digafmedia.domain.withyou;

import java.time.OffsetDateTime;

public record WithYouTestimonial(
    Long id,
    String title,
    String story,
    String authorName,
    String authorLocation,
    Boolean isApproved,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt
) {}
