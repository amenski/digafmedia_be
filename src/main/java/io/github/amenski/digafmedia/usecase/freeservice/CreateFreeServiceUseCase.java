package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;
import io.github.amenski.digafmedia.domain.rules.FreeServiceValidator;

public class CreateFreeServiceUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public CreateFreeServiceUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public FreeService invoke(FreeService service, CurrentUser currentUser) {
        FreeService toPersist = FreeService.withDefaults(service, currentUser.id());
        
        var validationResult = FreeServiceValidator.validate(toPersist);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        return freeServiceRepository.save(toPersist);
    }
}
