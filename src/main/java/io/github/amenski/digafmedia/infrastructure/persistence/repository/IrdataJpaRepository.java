package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.IrdataPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IrdataJpaRepository extends JpaRepository<IrdataPostEntity, Long> {
    List<IrdataPostEntity> findByStatus(IrdataStatus status);
    
    @Query("SELECT i FROM IrdataPostEntity i ORDER BY i.createdAt DESC LIMIT :limit")
    List<IrdataPostEntity> findRecent(@Param("limit") int limit);
    
    @Query("SELECT i FROM IrdataPostEntity i WHERE i.status = :status ORDER BY i.createdAt DESC LIMIT :limit")
    List<IrdataPostEntity> findRecentByStatus(@Param("status") IrdataStatus status, @Param("limit") int limit);
}
