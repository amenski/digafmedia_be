package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.AfalgunPostEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfalgunJpaRepository extends JpaRepository<AfalgunPostEntity, Long> {
    List<AfalgunPostEntity> findByStatus(AfalgunStatus status);
    List<AfalgunPostEntity> findByStatus(AfalgunStatus status, Pageable pageable);
    long countByStatus(AfalgunStatus status);
}
