package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.TikomaAlertEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TikomaJpaRepository extends JpaRepository<TikomaAlertEntity, Long> {
    List<TikomaAlertEntity> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);
}
