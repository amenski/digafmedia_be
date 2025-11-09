package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonials;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class GetAllWithYouTestimonialsUseCase {

    private final WithYouRepository withYouRepository;

    public GetAllWithYouTestimonialsUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public WithYouTestimonials invoke(Boolean approvedOnly, Integer page, Integer size) {
        if (page != null && size != null) {
            return getPaginatedTestimonials(approvedOnly, page, size);
        } else {
            return getNonPaginatedTestimonials(approvedOnly);
        }
    }

    private WithYouTestimonials getPaginatedTestimonials(Boolean approvedOnly, int page, int size) {
        if (approvedOnly != null && approvedOnly) {
            var testimonials = withYouRepository.findByApprovedPaginated(true, page, size);
            var total = withYouRepository.countByApproved(true);
            return WithYouTestimonials.of(testimonials, page, size, total);
        } else {
            var testimonials = withYouRepository.findAllPaginated(page, size);
            var total = withYouRepository.count();
            return WithYouTestimonials.of(testimonials, page, size, total);
        }
    }

    private WithYouTestimonials getNonPaginatedTestimonials(Boolean approvedOnly) {
        if (approvedOnly != null && approvedOnly) {
            return new WithYouTestimonials(withYouRepository.findApproved());
        }
        return new WithYouTestimonials(withYouRepository.findAll());
    }
}
