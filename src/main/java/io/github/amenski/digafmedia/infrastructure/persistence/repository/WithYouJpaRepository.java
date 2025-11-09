package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.WithYouTestimonialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithYouJpaRepository extends JpaRepository<WithYouTestimonialEntity, Long> {
    List<WithYouTestimonialEntity> findByIsApproved(Boolean isApproved);
    
    Page<WithYouTestimonialEntity> findAll(Pageable pageable);
    
    Page<WithYouTestimonialEntity> findByIsApproved(Boolean isApproved, Pageable pageable);
    
    long countByIsApproved(Boolean isApproved);
}
