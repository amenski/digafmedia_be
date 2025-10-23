package io.github.amenski.digafmedia.domain.withyou;

import io.github.amenski.digafmedia.domain.Validator;

public class WithYouTestimonialValidator implements Validator<WithYouTestimonial> {

    @Override
    public void validate(WithYouTestimonial testimonial) {
        if (testimonial == null) {
            throw new IllegalArgumentException("Testimonial cannot be null");
        }
        if (testimonial.title() == null || testimonial.title().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (testimonial.story() == null || testimonial.story().trim().isEmpty()) {
            throw new IllegalArgumentException("Story cannot be empty");
        }
        // Minimum story length for meaningful testimonials
        if (testimonial.story().trim().length() < 20) {
            throw new IllegalArgumentException("Story must be at least 20 characters long");
        }
        // Author information is optional but recommended
    }
}
