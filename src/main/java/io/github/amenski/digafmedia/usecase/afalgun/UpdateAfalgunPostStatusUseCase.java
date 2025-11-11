package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.AuthorizationException;
import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class UpdateAfalgunPostStatusUseCase {

    private final AfalgunRepository afalgunRepository;

    public UpdateAfalgunPostStatusUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public AfalgunPost invoke(Long id, AfalgunStatus status, CurrentUser currentUser) {
        // Only admins can update post status
        if (!currentUser.hasRole("ADMIN")) {
            throw AuthorizationException.forOperation("update afalgun post status");
        }

        AfalgunPost existing = afalgunRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.forEntity("AfalgunPost", id));

        AfalgunPost updated = existing.updateStatus(status);
        return afalgunRepository.save(updated);
    }
}
