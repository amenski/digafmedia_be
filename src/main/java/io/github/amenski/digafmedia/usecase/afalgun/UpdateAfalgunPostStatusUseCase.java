package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.afalgun.UpdateAfalgunPostCommand;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;

import java.time.OffsetDateTime;

public class UpdateAfalgunPostStatusUseCase {

    private final AfalgunRepository afalgunRepository;

    public UpdateAfalgunPostStatusUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public AfalgunPost execute(Long id, UpdateAfalgunPostCommand command, CurrentUserAdapter currentUser) {
        AfalgunPost existing = afalgunRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Afalgun post not found with id: " + id));

        AfalgunPost updated = new AfalgunPost(
                existing.id(),
                existing.missingPersonName(),
                existing.age(),
                existing.lastSeenLocation(),
                existing.contactName(),
                existing.contactPhone(),
                existing.contactEmail(),
                existing.description(),
                command.status(),
                existing.createdAt(),
                OffsetDateTime.now()
        );

        return afalgunRepository.save(updated);
    }
}
