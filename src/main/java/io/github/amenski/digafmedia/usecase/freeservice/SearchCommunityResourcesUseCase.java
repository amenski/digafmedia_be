package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.resource.CommunityResource;
import io.github.amenski.digafmedia.domain.repository.CommunityResourceRepository;

public class SearchCommunityResourcesUseCase {

    private final CommunityResourceRepository freeServiceRepository;

    public SearchCommunityResourcesUseCase(CommunityResourceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public PagedResult<CommunityResource> invoke(String query, int page, int size) {
        // For now, just return all resources - can be enhanced with search logic later
        var resources = freeServiceRepository.findAllPaginated(page, size);
        var totalElements = freeServiceRepository.count();
        return new PagedResult<>(resources, totalElements, page, size);
    }
}
