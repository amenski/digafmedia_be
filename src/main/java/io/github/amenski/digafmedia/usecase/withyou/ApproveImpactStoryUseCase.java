package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.story.ImpactStory;
import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;

public class ApproveImpactStoryUseCase {

    private final ImpactStoryRepository impactStoryRepository;

    public ApproveImpactStoryUseCase(ImpactStoryRepository impactStoryRepository) {
        this.impactStoryRepository = impactStoryRepository;
    }

    public ImpactStory invoke(Long id, CurrentUser currentUser) {
        ImpactStory story = impactStoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Impact story not found"));

        ImpactStory approvedStory = story.updateApproval(Boolean.TRUE);
        return impactStoryRepository.save(approvedStory);
    }
}
