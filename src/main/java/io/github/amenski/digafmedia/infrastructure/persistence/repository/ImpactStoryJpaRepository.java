package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.ImpactStoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImpactStoryJpaRepository extends JpaRepository<ImpactStoryEntity, Long> {
    List<ImpactStoryEntity> findByIsApproved(Boolean isApproved);
    List<ImpactStoryEntity> findByIsApproved(Boolean isApproved, Pageable pageable);
    List<ImpactStoryEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
