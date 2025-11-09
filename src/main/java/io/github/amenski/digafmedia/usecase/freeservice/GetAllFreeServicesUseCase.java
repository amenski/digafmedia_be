package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.freeservice.FreeServices;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class GetAllFreeServicesUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public GetAllFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public FreeServices invoke(Boolean isActive, Integer page, Integer size) {
        if (page != null && size != null) {
            return getPaginatedServices(isActive, page, size);
        } else {
            return getNonPaginatedServices(isActive);
        }
    }

    private FreeServices getPaginatedServices(Boolean isActive, int page, int size) {
        if (isActive == null) {
            var services = freeServiceRepository.findAllPaginated(page, size);
            var total = freeServiceRepository.count();
            return FreeServices.of(services, page, size, total);
        } else {
            var services = freeServiceRepository.findByActivePaginated(isActive, page, size);
            var total = freeServiceRepository.countByActive(isActive);
            return FreeServices.of(services, page, size, total);
        }
    }

    private FreeServices getNonPaginatedServices(Boolean isActive) {
        if (isActive != null) {
            return new FreeServices(freeServiceRepository.findByActive(isActive));
        }
        return new FreeServices(freeServiceRepository.findAll());
    }
}
