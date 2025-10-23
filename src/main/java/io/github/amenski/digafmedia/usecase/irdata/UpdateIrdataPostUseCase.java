package io.github.amenski.digafmedia.usecase.irdata;

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
                .orElseThrow(() -> new IllegalArgumentException("Irdata post not found with id: " + id));

        IrdataPost updated = new IrdataPost(
                existing.id(),
                existing.title(),
                existing.description(),
                existing.goalAmount(),
                newCurrentAmount != null ? newCurrentAmount : existing.currentAmount(),
                existing.bankName(),
                existing.accountNumber(),
                existing.accountHolder(),
                existing.contactName(),
                existing.contactPhone(),
                existing.contactEmail(),
                newStatus != null ? newStatus : existing.status(),
                existing.createdAt(),
                existing.modifiedAt()
        );

        return irdataRepository.save(updated);
    }
}
