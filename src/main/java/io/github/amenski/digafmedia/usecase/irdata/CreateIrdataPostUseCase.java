package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

import java.math.BigDecimal;

public class CreateIrdataPostUseCase {

    private final IrdataRepository irdataRepository;
    private final Validator<IrdataPost> irdataPostValidator;

    public CreateIrdataPostUseCase(IrdataRepository irdataRepository, Validator<IrdataPost> irdataPostValidator) {
        this.irdataRepository = irdataRepository;
        this.irdataPostValidator = irdataPostValidator;
    }

    public IrdataPost invoke(IrdataPost post) {
        // Set defaults if missing
        IrdataPost toPersist = post;
        if (post.status() == null || post.currentAmount() == null) {
            toPersist = new IrdataPost(
                    post.id(),
                    post.title(),
                    post.description(),
                    post.goalAmount(),
                    post.currentAmount() != null ? post.currentAmount() : BigDecimal.ZERO,
                    post.bankName(),
                    post.accountNumber(),
                    post.accountHolder(),
                    post.contactName(),
                    post.contactPhone(),
                    post.contactEmail(),
                    post.status() != null ? post.status() : IrdataStatus.ACTIVE,
                    post.createdAt(),
                    post.modifiedAt()
            );
        }
        irdataPostValidator.validate(toPersist);
        return irdataRepository.save(toPersist);
    }
}
