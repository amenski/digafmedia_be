package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

public class DeleteIrdataPostUseCase {

    private final IrdataRepository irdataRepository;

    public DeleteIrdataPostUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public void invoke(Long id) {
        // Check if entity exists before deletion
        if (!irdataRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("IrdataPost", id);
        }
        irdataRepository.deleteById(id);
    }
}
