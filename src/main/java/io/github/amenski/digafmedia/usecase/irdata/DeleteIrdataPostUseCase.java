package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

public class DeleteIrdataPostUseCase {

    private final IrdataRepository irdataRepository;

    public DeleteIrdataPostUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public void invoke(Long id) {
        irdataRepository.deleteById(id);
    }
}
