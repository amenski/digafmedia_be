package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

import java.util.Optional;

public class GetIrdataPostByIdUseCase {

    private final IrdataRepository irdataRepository;

    public GetIrdataPostByIdUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public Optional<IrdataPost> invoke(Long id) {
        return irdataRepository.findById(id);
    }
}
