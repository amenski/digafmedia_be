package io.github.amenski.digafmedia.usecase.withyou;

import io.github.amenski.digafmedia.domain.story.ImpactStory;
import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;

import java.util.Optional;

public class GetImpactStoryByIdUseCase {

    private final ImpactStoryRepository impactStoryRepository;

    public GetImpactStoryByIdUseCase(ImpactStoryRepository impactStoryRepository) {
        this.impactStoryRepository = impactStoryRepository;
    }

    public Optional<ImpactStory> invoke(Long id) {
        return impactStoryRepository.findById(id);
    }
}
