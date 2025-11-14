package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;
import io.github.amenski.digafmedia.domain.rules.WithYouValidator;

public class CreateWithYouTestimonialUseCase {

    private final WithYouRepository withYouRepository;

    public CreateWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
        this.withYouRepository = withYouRepository;
    }

    public WithYouTestimonial invoke(WithYouTestimonial testimonial, CurrentUser currentUser) {
        WithYouTestimonial toPersist = WithYouTestimonial.withDefaults(testimonial, currentUser.id());
        
        var validationResult = WithYouValidator.validate(toPersist);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        return withYouRepository.save(toPersist);
    }
}
