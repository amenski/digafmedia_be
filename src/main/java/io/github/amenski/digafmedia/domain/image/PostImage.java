package io.github.amenski.digafmedia.domain.image;

import java.time.OffsetDateTime;

public record PostImage(
    Long id,
    PostType postType,
    Long postId,
    String filePath,
    String altText,
    Integer displayOrder,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt
) {}
