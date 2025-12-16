package io.github.amenski.digafmedia.usecase.fundraising;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;

public class UpdateFundraisingCampaignStatusUseCase {
    
    private final FundraisingCampaignRepository fundraisingCampaignRepository;

    public UpdateFundraisingCampaignStatusUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
        this.fundraisingCampaignRepository = fundraisingCampaignRepository;
    }

    public FundraisingCampaign invoke(Long id, CampaignStatus newStatus, CurrentUser currentUser) {
        FundraisingCampaign campaign = fundraisingCampaignRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fundraising campaign not found"));
        
        FundraisingCampaign updatedCampaign = campaign.updateStatus(newStatus);
        return fundraisingCampaignRepository.save(updatedCampaign);
    }
}
