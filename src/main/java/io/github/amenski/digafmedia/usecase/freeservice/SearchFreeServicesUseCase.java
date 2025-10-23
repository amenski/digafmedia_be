package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.freeservice.FreeServices;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class SearchFreeServicesUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public SearchFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public FreeServices invoke(String category, String location) {
        if (category != null && !category.trim().isEmpty()) {
            return new FreeServices(freeServiceRepository.findByCategory(category));
        }
        if (location != null && !location.trim().isEmpty()) {
            return new FreeServices(freeServiceRepository.findByLocationContaining(location));
        }
        return new FreeServices(freeServiceRepository.findByActive(true));
    }
}
