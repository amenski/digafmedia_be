package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.alert.CreateCommunityAlertCommand;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;
import io.github.amenski.digafmedia.domain.rules.CommunityAlertValidator;

public class CreateCommunityAlertUseCase {
    
    private final CommunityAlertRepository communityAlertRepository;

    public CreateCommunityAlertUseCase(CommunityAlertRepository communityAlertRepository) {
        this.communityAlertRepository = communityAlertRepository;
    }

    public CommunityAlert execute(CreateCommunityAlertCommand command, CurrentUser currentUser) {
        var validationResult = CommunityAlertValidator.validateCreateCommand(command);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        CommunityAlert alert = CommunityAlert.fromCommand(command, currentUser.id());
        return communityAlertRepository.save(alert);
    }
}
