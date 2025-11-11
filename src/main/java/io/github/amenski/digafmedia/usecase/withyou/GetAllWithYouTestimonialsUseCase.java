package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class GetAllWithYouTestimonialsUseCase {

    private final WithYouRepository withYouRepository;

    public GetAllWithYouTestimonialsUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public PagedResult<WithYouTestimonial> invoke(Boolean approvedOnly, int page, int size) {
        if (approvedOnly != null && approvedOnly) {
            var testimonials = withYouRepository.findApprovedPaginated(page, size);
            var totalElements = withYouRepository.countApproved();
            return new PagedResult<>(testimonials, totalElements, page, size);
        }
        var testimonials = withYouRepository.findAllPaginated(page, size);
        var totalElements = withYouRepository.count();
        return new PagedResult<>(testimonials, totalElements, page, size);
    }
}
