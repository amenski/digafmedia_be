package io.github.amenski.digafmedia.domain;

import java.time.OffsetDateTime;

public record Comment(
    Long id,
    String name,
    String email,
    String content,
    OffsetDateTime createdAt
) {
}