package io.github.amenski.digafmedia.domain.withyou;

import java.util.List;

public record WithYouTestimonials(List<WithYouTestimonial> testimonials) {
    
    public static WithYouTestimonials of(List<WithYouTestimonial> testimonials) {
        return new WithYouTestimonials(testimonials);
    }
}
