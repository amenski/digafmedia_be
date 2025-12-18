package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.FundraisingCampaignEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundraisingCampaignJpaRepository extends JpaRepository<FundraisingCampaignEntity, Long> {
    List<FundraisingCampaignEntity> findByStatus(CampaignStatus status);
    
    Page<FundraisingCampaignEntity> findByStatus(CampaignStatus status, Pageable pageable);
    
    @Query("SELECT f FROM FundraisingCampaignEntity f ORDER BY f.createdAt DESC LIMIT :limit")
    List<FundraisingCampaignEntity> findRecent(@Param("limit") int limit);
    
    @Query("SELECT f FROM FundraisingCampaignEntity f WHERE f.status = :status ORDER BY f.createdAt DESC LIMIT :limit")
    List<FundraisingCampaignEntity> findRecentByStatus(@Param("status") CampaignStatus status, @Param("limit") int limit);
    
    long countByStatus(CampaignStatus status);
}
