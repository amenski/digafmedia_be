package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

import java.math.BigDecimal;

public class UpdateIrdataPostUseCase {

    private final IrdataRepository irdataRepository;

    public UpdateIrdataPostUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public IrdataPost invoke(Long id, IrdataStatus newStatus, BigDecimal newCurrentAmount) {
        IrdataPost existing = irdataRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.forEntity("IrdataPost", id));

        IrdataPost updated = existing.update(newStatus, newCurrentAmount);
        return irdataRepository.save(updated);
    }
}
