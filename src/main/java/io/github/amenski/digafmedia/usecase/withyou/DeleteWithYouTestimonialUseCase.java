package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class DeleteWithYouTestimonialUseCase {

    private final WithYouRepository withYouRepository;

    public DeleteWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public void invoke(Long id) {
        withYouRepository.deleteById(id);
    }
}
