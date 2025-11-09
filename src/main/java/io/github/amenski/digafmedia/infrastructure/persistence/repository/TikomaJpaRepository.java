package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.TikomaAlertEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TikomaJpaRepository extends JpaRepository<TikomaAlertEntity, Long> {
    List<TikomaAlertEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<TikomaAlertEntity> findByUrgency(TikomaUrgency urgency);

    @Query("SELECT t FROM TikomaAlertEntity t ORDER BY t.createdAt DESC")
    List<TikomaAlertEntity> findAllPaginated(Pageable pageable);

    @Query("SELECT t FROM TikomaAlertEntity t WHERE t.urgency = :urgency ORDER BY t.createdAt DESC")
    List<TikomaAlertEntity> findByUrgencyPaginated(@Param("urgency") TikomaUrgency urgency, Pageable pageable);

    @Query("SELECT COUNT(t) FROM TikomaAlertEntity t WHERE t.urgency = :urgency")
    long countByUrgency(@Param("urgency") TikomaUrgency urgency);
}
