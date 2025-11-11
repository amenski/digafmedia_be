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
) {
    /**
     * Factory method to create a WithYouTestimonial with default isApproved if not provided.
     * This encapsulates the business rule that new testimonials default to not approved.
     */
    public static WithYouTestimonial withDefaults(WithYouTestimonial testimonial) {
        if (testimonial.isApproved() != null) {
            return testimonial;
        }
        return new WithYouTestimonial(
                testimonial.id(),
                testimonial.title(),
                testimonial.story(),
                testimonial.authorName(),
                testimonial.authorLocation(),
                false,
                testimonial.createdAt(),
                testimonial.modifiedAt()
        );
    }

    /**
     * Domain method to approve a testimonial.
     * Encapsulates the business rule for approval state change.
     */
    public WithYouTestimonial approve() {
        return new WithYouTestimonial(
                this.id(),
                this.title(),
                this.story(),
                this.authorName(),
                this.authorLocation(),
                true,
                this.createdAt(),
                this.modifiedAt()
        );
    }
}
