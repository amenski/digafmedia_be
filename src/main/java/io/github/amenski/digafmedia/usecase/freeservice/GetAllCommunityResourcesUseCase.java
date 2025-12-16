package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.resource.CommunityResource;
import io.github.amenski.digafmedia.domain.repository.CommunityResourceRepository;

public class GetAllCommunityResourcesUseCase {

    private final CommunityResourceRepository freeServiceRepository;

    public GetAllCommunityResourcesUseCase(CommunityResourceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public PagedResult<CommunityResource> invoke(int page, int size) {
        var resources = freeServiceRepository.findAllPaginated(page, size);
        var totalElements = freeServiceRepository.count();
        return new PagedResult<>(resources, totalElements, page, size);
    }
}
