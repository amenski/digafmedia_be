package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class CreateFreeServiceUseCase {

    private final FreeServiceRepository freeServiceRepository;
    private final Validator<FreeService> freeServiceValidator;

    public CreateFreeServiceUseCase(FreeServiceRepository freeServiceRepository, Validator<FreeService> freeServiceValidator) {
        this.freeServiceRepository = freeServiceRepository;
        this.freeServiceValidator = freeServiceValidator;
    }

    public FreeService invoke(FreeService service) {
        // Set default isActive if missing
        FreeService toPersist = service;
        if (service.isActive() == null) {
            toPersist = new FreeService(
                    service.id(),
                    service.serviceName(),
                    service.providerName(),
                    service.description(),
                    service.location(),
                    service.contactPhone(),
                    service.contactEmail(),
                    service.category(),
                    service.hoursOfOperation(),
                    true,
                    service.createdAt(),
                    service.modifiedAt()
            );
        }
        freeServiceValidator.validate(toPersist);
        return freeServiceRepository.save(toPersist);
    }
}
