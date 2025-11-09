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
        // Business rule: return a reasonable number of records for performance
        // without exposing pagination details to the use case
        final int DEFAULT_LIMIT = 50;
        
        if (status == null) {
            return new IrdataPosts(irdataRepository.findRecent(DEFAULT_LIMIT));
        }
        return new IrdataPosts(irdataRepository.findRecentByStatus(status, DEFAULT_LIMIT));
    }
}
