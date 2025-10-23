package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.freeservice.FreeServices;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class GetAllFreeServicesUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public GetAllFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public FreeServices invoke(Boolean isActive) {
        if (isActive != null) {
            return new FreeServices(freeServiceRepository.findByActive(isActive));
        }
        return new FreeServices(freeServiceRepository.findAll());
    }
}
