package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonials;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class GetAllWithYouTestimonialsUseCase {

    private final WithYouRepository withYouRepository;

    public GetAllWithYouTestimonialsUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public WithYouTestimonials invoke(Boolean approvedOnly) {
        if (approvedOnly != null && approvedOnly) {
            return new WithYouTestimonials(withYouRepository.findApproved());
        }
        return new WithYouTestimonials(withYouRepository.findAll());
    }
}
