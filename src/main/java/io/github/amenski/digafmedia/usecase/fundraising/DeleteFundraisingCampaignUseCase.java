package io.github.amenski.digafmedia.usecase.fundraising;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;

public class DeleteFundraisingCampaignUseCase {

    private final FundraisingCampaignRepository fundraisingCampaignRepository;

    public DeleteFundraisingCampaignUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
        this.fundraisingCampaignRepository = fundraisingCampaignRepository;
    }

    public void invoke(Long id, CurrentUser currentUser) {
        if (!fundraisingCampaignRepository.existsById(id)) {
            throw new RuntimeException("Fundraising campaign not found");
        }

        fundraisingCampaignRepository.deleteById(id);
    }
}
