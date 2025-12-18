package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.CommunityResourceEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityResourceJpaRepository extends JpaRepository<CommunityResourceEntity, Long> {
    List<CommunityResourceEntity> findByIsActive(Boolean isActive);
    List<CommunityResourceEntity> findByIsActive(Boolean isActive, Pageable pageable);
    List<CommunityResourceEntity> findByCategory(String category);
    List<CommunityResourceEntity> findByLocationContaining(String location);
    List<CommunityResourceEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
