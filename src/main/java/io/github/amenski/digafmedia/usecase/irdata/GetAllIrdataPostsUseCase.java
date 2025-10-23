package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.irdata.IrdataPosts;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

public class GetAllIrdataPostsUseCase {

    private final IrdataRepository irdataRepository;

    public GetAllIrdataPostsUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public IrdataPosts invoke(IrdataStatus status) {
        if (status == null) {
            return new IrdataPosts(irdataRepository.findAll());
        }
        return new IrdataPosts(irdataRepository.findByStatus(status));
    }
}
