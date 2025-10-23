package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.AfalgunPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AfalgunJpaRepository extends JpaRepository<AfalgunPostEntity, Long> {
    List<AfalgunPostEntity> findByStatus(AfalgunStatus status);
}
