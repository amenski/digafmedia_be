package io.github.amenski.digafmedia.domain;

import java.time.OffsetDateTime;

public record Comment(
    Long id,
    String name,
    String email,
    String content,
    OffsetDateTime createdAt
) {
    /**
     * Factory method to create a Comment with default createdAt if not provided.
     * This encapsulates the business rule that new comments should have a creation timestamp.
     */
    public static Comment withDefaults(Comment comment) {
        if (comment.createdAt() != null) {
            return comment;
        }
        return new Comment(
                comment.id(),
                comment.name(),
                comment.email(),
                comment.content(),
                OffsetDateTime.now()
        );
    }
}