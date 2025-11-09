package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.FreeServiceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeServiceJpaRepository extends JpaRepository<FreeServiceEntity, Long> {
    List<FreeServiceEntity> findByIsActive(Boolean isActive);
    List<FreeServiceEntity> findByIsActive(Boolean isActive, Pageable pageable);
    List<FreeServiceEntity> findByCategory(String category);
    List<FreeServiceEntity> findByLocationContaining(String location);
    List<FreeServiceEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
