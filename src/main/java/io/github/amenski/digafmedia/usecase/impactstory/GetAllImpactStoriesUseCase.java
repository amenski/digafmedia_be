package io.github.amenski.digafmedia.usecase.impactstory;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.story.ImpactStory;
import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;

public class GetAllImpactStoriesUseCase {

    private final ImpactStoryRepository impactStoryRepository;

    public GetAllImpactStoriesUseCase(ImpactStoryRepository impactStoryRepository) {
        this.impactStoryRepository = impactStoryRepository;
    }

    public PagedResult<ImpactStory> invoke(int page, int size) {
        var stories = impactStoryRepository.findAllPaginated(page, size);
        var totalElements = impactStoryRepository.count();
        return new PagedResult<>(stories, totalElements, page, size);
    }
}
