package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.afalgun.CreateAfalgunPostCommand;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;

import java.time.OffsetDateTime;

public class CreateAfalgunPostUseCase {

    private final AfalgunRepository afalgunRepository;
    private final Validator<AfalgunPost> afalgunPostValidator;

    public CreateAfalgunPostUseCase(AfalgunRepository afalgunRepository, Validator<AfalgunPost> afalgunPostValidator) {
        this.afalgunRepository = afalgunRepository;
        this.afalgunPostValidator = afalgunPostValidator;
    }

    public AfalgunPost execute(CreateAfalgunPostCommand command, CurrentUserAdapter currentUser) {
        AfalgunPost post = new AfalgunPost(
                null,
                command.missingPersonName(),
                command.age(),
                command.lastSeenLocation(),
                command.contactName(),
                command.contactPhone(),
                command.contactEmail(),
                command.description(),
                command.status(),
                OffsetDateTime.now(),
                null
        );
        
        afalgunPostValidator.validate(post);
        return afalgunRepository.save(post);
    }
}
