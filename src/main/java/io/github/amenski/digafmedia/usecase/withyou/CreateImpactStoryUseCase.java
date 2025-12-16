package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.story.ImpactStory;
import io.github.amenski.digafmedia.domain.story.CreateImpactStoryCommand;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;
import io.github.amenski.digafmedia.domain.rules.ImpactStoryValidator;

public class CreateImpactStoryUseCase {

    private final ImpactStoryRepository impactStoryRepository;

    public CreateImpactStoryUseCase(ImpactStoryRepository impactStoryRepository) {
        this.impactStoryRepository = impactStoryRepository;
    }

    public ImpactStory execute(CreateImpactStoryCommand command, CurrentUser currentUser) {
        var validationResult = ImpactStoryValidator.validateCreateCommand(command);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }

        ImpactStory story = ImpactStory.fromCommand(command, currentUser.id());
        return impactStoryRepository.save(story);
    }
}
