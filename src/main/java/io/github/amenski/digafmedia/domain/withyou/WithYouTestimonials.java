package io.github.amenski.digafmedia.domain.withyou;

import io.github.amenski.digafmedia.domain.Pagination;
import java.util.List;

public record WithYouTestimonials(List<WithYouTestimonial> testimonials, Pagination pagination) {
    
    public WithYouTestimonials(List<WithYouTestimonial> testimonials) {
        this(testimonials, null);
    }
    
    public static WithYouTestimonials of(List<WithYouTestimonial> testimonials, int page, int size, long totalElements) {
        Pagination pagination = Pagination.of(page, size, totalElements);
        return new WithYouTestimonials(testimonials, pagination);
    }
}
