package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.resource.CommunityResource;
import io.github.amenski.digafmedia.domain.resource.CreateCommunityResourceCommand;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.CommunityResourceRepository;
import io.github.amenski.digafmedia.domain.rules.CommunityResourceValidator;

public class CreateCommunityResourceUseCase {
    
    private final CommunityResourceRepository freeServiceRepository;

    public CreateCommunityResourceUseCase(CommunityResourceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public CommunityResource execute(CreateCommunityResourceCommand command, CurrentUser currentUser) {
        var validationResult = CommunityResourceValidator.validateCreateCommand(command);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        CommunityResource resource = CommunityResource.fromCommand(command, currentUser.id());
        return freeServiceRepository.save(resource);
    }
}
