package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class DeleteFreeServiceUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public DeleteFreeServiceUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public void invoke(Long id) {
        // Check if entity exists before deletion
        if (!freeServiceRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("FreeService", id);
        }
        freeServiceRepository.deleteById(id);
    }
}
