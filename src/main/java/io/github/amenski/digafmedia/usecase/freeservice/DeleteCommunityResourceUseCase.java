package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.CommunityResourceRepository;

public class DeleteCommunityResourceUseCase {

    private final CommunityResourceRepository communityResourceRepository;

    public DeleteCommunityResourceUseCase(CommunityResourceRepository communityResourceRepository) {
        this.communityResourceRepository = communityResourceRepository;
    }

    public void invoke(Long id) {
        // Check if entity exists before deletion
        if (!communityResourceRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("CommunityResource", id);
        }
        communityResourceRepository.deleteById(id);
    }
}
