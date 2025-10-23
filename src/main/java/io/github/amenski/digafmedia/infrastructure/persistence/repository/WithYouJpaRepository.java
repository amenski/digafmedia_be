package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.WithYouTestimonialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithYouJpaRepository extends JpaRepository<WithYouTestimonialEntity, Long> {
    List<WithYouTestimonialEntity> findByIsApproved(Boolean isApproved);
}
