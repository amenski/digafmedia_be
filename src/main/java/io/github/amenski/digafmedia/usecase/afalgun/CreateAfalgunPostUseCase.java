package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

import java.time.OffsetDateTime;

public class CreateAfalgunPostUseCase {

    private final AfalgunRepository afalgunRepository;
    private final Validator<AfalgunPost> afalgunPostValidator;

    public CreateAfalgunPostUseCase(AfalgunRepository afalgunRepository, Validator<AfalgunPost> afalgunPostValidator) {
        this.afalgunRepository = afalgunRepository;
        this.afalgunPostValidator = afalgunPostValidator;
    }

    public AfalgunPost invoke(AfalgunPost post) {
        // Set defaults if missing
        AfalgunPost toPersist = post;
        if (post.status() == null) {
            toPersist = new AfalgunPost(
                    post.id(),
                    post.missingPersonName(),
                    post.age(),
                    post.lastSeenLocation(),
                    post.contactName(),
                    post.contactPhone(),
                    post.contactEmail(),
                    post.description(),
                    AfalgunStatus.ACTIVE,
                    post.createdAt(),
                    post.modifiedAt()
            );
        }
        afalgunPostValidator.validate(toPersist);
        return afalgunRepository.save(toPersist);
    }
}
