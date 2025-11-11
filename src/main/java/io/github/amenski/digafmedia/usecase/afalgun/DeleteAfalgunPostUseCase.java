package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.AuthorizationException;
import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class DeleteAfalgunPostUseCase {

    private final AfalgunRepository afalgunRepository;

    public DeleteAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public void invoke(Long id, CurrentUser currentUser) {
        // Only admins can delete posts
        if (!currentUser.hasRole("ADMIN")) {
            throw AuthorizationException.forOperation("delete afalgun post");
        }

        // Check if entity exists before deletion
        if (!afalgunRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("AfalgunPost", id);
        }
        afalgunRepository.deleteById(id);
    }
}
