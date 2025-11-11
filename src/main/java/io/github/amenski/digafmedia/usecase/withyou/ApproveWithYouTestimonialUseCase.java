package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.AuthorizationException;
import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class ApproveWithYouTestimonialUseCase {

    private final WithYouRepository withYouRepository;

    public ApproveWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public WithYouTestimonial invoke(Long id, CurrentUser currentUser) {
        // Only admins can approve testimonials
        if (!currentUser.hasRole("ADMIN")) {
            throw AuthorizationException.forOperation("approve testimonial");
        }

        WithYouTestimonial existing = withYouRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.forEntity("WithYouTestimonial", id));

        WithYouTestimonial approved = existing.approve();
        return withYouRepository.save(approved);
    }
}
