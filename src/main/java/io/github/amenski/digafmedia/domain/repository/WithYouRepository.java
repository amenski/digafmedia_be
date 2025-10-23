package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;

import java.util.List;
import java.util.Optional;

public interface WithYouRepository {

    List<WithYouTestimonial> findAll();

    List<WithYouTestimonial> findApproved();

    Optional<WithYouTestimonial> findById(Long id);

    WithYouTestimonial save(WithYouTestimonial testimonial);

    void deleteById(Long id);
}
