package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.CreateAfalgunPostCommand;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;
import io.github.amenski.digafmedia.domain.rules.AfalgunValidator;

public class CreateAfalgunPostUseCase {
    
    private final AfalgunRepository afalgunRepository;

    public CreateAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public AfalgunPost execute(CreateAfalgunPostCommand command, CurrentUser currentUser) {
        var validationResult = AfalgunValidator.validateCreateCommand(command);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        AfalgunPost post = AfalgunPost.fromCommand(command);
        return afalgunRepository.save(post);
    }
}
