package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;

import java.util.List;
import java.util.Optional;

public interface FundraisingCampaignRepository {

    List<FundraisingCampaign> findRecent(int limit);

    List<FundraisingCampaign> findRecentByStatus(CampaignStatus status, int limit);

    List<FundraisingCampaign> findAllPaginated(int page, int size);

    List<FundraisingCampaign> findByStatusPaginated(CampaignStatus status, int page, int size);

    long count();

    long countByStatus(CampaignStatus status);

    Optional<FundraisingCampaign> findById(Long id);

    boolean existsById(Long id);

    FundraisingCampaign save(FundraisingCampaign campaign);

    void deleteById(Long id);
}
