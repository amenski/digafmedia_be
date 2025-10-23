package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.IrdataPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IrdataJpaRepository extends JpaRepository<IrdataPostEntity, Long> {
    List<IrdataPostEntity> findByStatus(IrdataStatus status);
}
