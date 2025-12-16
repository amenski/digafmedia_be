package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;

public class DeleteImpactStoryUseCase {

    private final ImpactStoryRepository impactStoryRepository;

    public DeleteImpactStoryUseCase(ImpactStoryRepository impactStoryRepository) {
        this.impactStoryRepository = impactStoryRepository;
    }

    public void invoke(Long id, CurrentUser currentUser) {
        if (!impactStoryRepository.existsById(id)) {
            throw new RuntimeException("Impact story not found");
        }

        impactStoryRepository.deleteById(id);
    }
}
