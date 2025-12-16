package io.github.amenski.digafmedia.usecase.fundraising;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;

import java.math.BigDecimal;

public class UpdateFundraisingCampaignAmountUseCase {

    private final FundraisingCampaignRepository fundraisingCampaignRepository;

    public UpdateFundraisingCampaignAmountUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
        this.fundraisingCampaignRepository = fundraisingCampaignRepository;
    }

    public FundraisingCampaign invoke(Long id, BigDecimal newAmount, CurrentUser currentUser) {
        FundraisingCampaign campaign = fundraisingCampaignRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fundraising campaign not found"));

        FundraisingCampaign updatedCampaign = campaign.updateAmount(newAmount);
        return fundraisingCampaignRepository.save(updatedCampaign);
    }
}
