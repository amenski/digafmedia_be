package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class ApproveWithYouTestimonialUseCase {

    private final WithYouRepository withYouRepository;

    public ApproveWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public WithYouTestimonial invoke(Long id) {
        WithYouTestimonial existing = withYouRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Testimonial not found with id: " + id));

        WithYouTestimonial approved = new WithYouTestimonial(
                existing.id(),
                existing.title(),
                existing.story(),
                existing.authorName(),
                existing.authorLocation(),
                true,
                existing.createdAt(),
                existing.modifiedAt()
        );

        return withYouRepository.save(approved);
    }
}
