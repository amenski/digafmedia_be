package io.github.amenski.digafmedia.usecase.fundraising;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.fundraising.SearchFundraisingCampaignsCommand;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;

public class GetAllFundraisingCampaignsUseCase {

    private final FundraisingCampaignRepository fundraisingCampaignRepository;

    public GetAllFundraisingCampaignsUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
        this.fundraisingCampaignRepository = fundraisingCampaignRepository;
    }

    public PagedResult<FundraisingCampaign> invoke(SearchFundraisingCampaignsCommand command) {
        if (command.status() != null) {
            var campaigns = fundraisingCampaignRepository.findByStatusPaginated(
                command.status(), command.page(), command.size());
            var totalElements = fundraisingCampaignRepository.countByStatus(command.status());
            return new PagedResult<>(campaigns, totalElements, command.page(), command.size());
        } else {
            var campaigns = fundraisingCampaignRepository.findAllPaginated(
                command.page(), command.size());
            var totalElements = fundraisingCampaignRepository.count();
            return new PagedResult<>(campaigns, totalElements, command.page(), command.size());
        }
    }
}
