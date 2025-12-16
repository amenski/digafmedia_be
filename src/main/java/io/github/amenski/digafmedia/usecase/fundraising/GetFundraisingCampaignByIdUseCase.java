package io.github.amenski.digafmedia.usecase.fundraising;

import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;

import java.util.Optional;

public class GetFundraisingCampaignByIdUseCase {
    
    private final FundraisingCampaignRepository fundraisingCampaignRepository;

    public GetFundraisingCampaignByIdUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
        this.fundraisingCampaignRepository = fundraisingCampaignRepository;
    }

    public Optional<FundraisingCampaign> invoke(Long id) {
        return fundraisingCampaignRepository.findById(id);
    }
}
