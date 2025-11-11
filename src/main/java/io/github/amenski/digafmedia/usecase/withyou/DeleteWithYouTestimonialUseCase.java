package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class DeleteWithYouTestimonialUseCase {

    private final WithYouRepository withYouRepository;

    public DeleteWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public void invoke(Long id) {
        // Check if entity exists before deletion
        if (!withYouRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("WithYouTestimonial", id);
        }
        withYouRepository.deleteById(id);
    }
}
