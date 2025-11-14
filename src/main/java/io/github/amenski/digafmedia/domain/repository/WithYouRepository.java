package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;

import java.util.List;
import java.util.Optional;

public interface WithYouRepository {

    List<WithYouTestimonial> findAll();

    List<WithYouTestimonial> findApproved();

    List<WithYouTestimonial> findAllPaginated(int page, int size);

    List<WithYouTestimonial> findByApprovedPaginated(boolean approved, int page, int size);

    List<WithYouTestimonial> findApprovedPaginated(int page, int size);

    long count();

    long countByApproved(boolean approved);

    long countApproved();

    Optional<WithYouTestimonial> findById(Long id);

    boolean existsById(Long id);

    WithYouTestimonial save(WithYouTestimonial testimonial);

    void deleteById(Long id);
}
