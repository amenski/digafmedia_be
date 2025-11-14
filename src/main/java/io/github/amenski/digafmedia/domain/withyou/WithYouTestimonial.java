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
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    public static WithYouTestimonial withDefaults(WithYouTestimonial testimonial, Long createdBy) {
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
                testimonial.modifiedAt(),
                createdBy
        );
    }

    public WithYouTestimonial approve() {
        return new WithYouTestimonial(
                this.id(),
                this.title(),
                this.story(),
                this.authorName(),
                this.authorLocation(),
                true,
                this.createdAt(),
                this.modifiedAt(),
                this.createdBy()
        );
    }
}
