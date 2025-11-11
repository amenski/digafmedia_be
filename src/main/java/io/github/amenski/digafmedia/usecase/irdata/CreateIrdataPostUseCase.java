package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;
import io.github.amenski.digafmedia.domain.rules.IrdataValidator;

public class CreateIrdataPostUseCase {

    private final IrdataRepository irdataRepository;

    public CreateIrdataPostUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public IrdataPost invoke(IrdataPost post) {
        var validationResult = IrdataValidator.validate(post);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }

        IrdataPost toPersist = IrdataPost.withDefaults(post);
        return irdataRepository.save(toPersist);
    }
}
