package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.alert.AlertUrgency;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.CommunityAlertEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityAlertJpaRepository extends JpaRepository<CommunityAlertEntity, Long> {
    List<CommunityAlertEntity> findByUrgency(AlertUrgency urgency);
    List<CommunityAlertEntity> findByUrgency(AlertUrgency urgency, Pageable pageable);
    List<CommunityAlertEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
