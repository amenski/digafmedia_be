package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class DeleteFreeServiceUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public DeleteFreeServiceUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public void invoke(Long id) {
        freeServiceRepository.deleteById(id);
    }
}
