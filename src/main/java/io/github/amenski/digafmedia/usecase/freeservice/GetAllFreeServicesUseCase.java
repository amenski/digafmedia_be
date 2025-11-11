package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class GetAllFreeServicesUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public GetAllFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public PagedResult<FreeService> invoke(Boolean isActive, int page, int size) {
        if (isActive != null) {
            var services = freeServiceRepository.findByActivePaginated(isActive, page, size);
            var totalElements = freeServiceRepository.countByActive(isActive);
            return new PagedResult<>(services, totalElements, page, size);
        }
        var services = freeServiceRepository.findAllPaginated(page, size);
        var totalElements = freeServiceRepository.count();
        return new PagedResult<>(services, totalElements, page, size);
    }
}
