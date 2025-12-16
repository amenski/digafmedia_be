package io.github.amenski.digafmedia.usecase.fundraising;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.fundraising.CreateFundraisingCampaignCommand;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;
import io.github.amenski.digafmedia.domain.rules.FundraisingCampaignValidator;

public class CreateFundraisingCampaignUseCase {
    
    private final FundraisingCampaignRepository fundraisingCampaignRepository;

    public CreateFundraisingCampaignUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
        this.fundraisingCampaignRepository = fundraisingCampaignRepository;
    }

    public FundraisingCampaign execute(CreateFundraisingCampaignCommand command, CurrentUser currentUser) {
        var validationResult = FundraisingCampaignValidator.validateCreateCommand(command);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        FundraisingCampaign campaign = FundraisingCampaign.fromCommand(command, currentUser.id());
        return fundraisingCampaignRepository.save(campaign);
    }
}
