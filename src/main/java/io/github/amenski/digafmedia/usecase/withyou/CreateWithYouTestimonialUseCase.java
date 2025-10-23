package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;

public class CreateWithYouTestimonialUseCase {

    private final WithYouRepository withYouRepository;
    private final Validator<WithYouTestimonial> withYouTestimonialValidator;

    public CreateWithYouTestimonialUseCase(WithYouRepository withYouRepository, Validator<WithYouTestimonial> withYouTestimonialValidator) {
        this.withYouRepository = withYouRepository;
        this.withYouTestimonialValidator = withYouTestimonialValidator;
    }

    public WithYouTestimonial invoke(WithYouTestimonial testimonial) {
        // New testimonials default to not approved
        WithYouTestimonial toPersist = testimonial;
        if (testimonial.isApproved() == null) {
            toPersist = new WithYouTestimonial(
                    testimonial.id(),
                    testimonial.title(),
                    testimonial.story(),
                    testimonial.authorName(),
                    testimonial.authorLocation(),
                    false,
                    testimonial.createdAt(),
                    testimonial.modifiedAt()
            );
        }
        withYouTestimonialValidator.validate(toPersist);
        return withYouRepository.save(toPersist);
    }
}
