package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;

public final class WithYouValidator {

    private WithYouValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validate(WithYouTestimonial testimonial) {
        ValidationResult result = new ValidationResult();
        
        if (testimonial == null) {
            result.addError("testimonial", "Testimonial cannot be null");
            return result;
        }
        
        if (testimonial.title() == null || testimonial.title().trim().isEmpty()) {
            result.addError("title", "Title cannot be empty");
        }
        
        if (testimonial.story() == null || testimonial.story().trim().isEmpty()) {
            result.addError("story", "Story cannot be empty");
        }
        
        // Minimum story length for meaningful testimonials
        if (testimonial.story() != null && testimonial.story().trim().length() < 20) {
            result.addError("story", "Story must be at least 20 characters long");
        }
        
        // Author information is optional but recommended
        // No validation for author fields as they are optional
        
        return result;
    }
}


