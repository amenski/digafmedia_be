package io.github.amenski.digafmedia.usecase.irdata;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;

public class GetAllIrdataPostsUseCase {

    private final IrdataRepository irdataRepository;

    public GetAllIrdataPostsUseCase(IrdataRepository irdataRepository) {
        this.irdataRepository = irdataRepository;
    }

    public PagedResult<IrdataPost> invoke(IrdataStatus status, int page, int size) {
        if (status != null) {
            var posts = irdataRepository.findByStatusPaginated(status, page, size);
            var totalElements = irdataRepository.countByStatus(status);
            return new PagedResult<>(posts, totalElements, page, size);
        }
        var posts = irdataRepository.findAllPaginated(page, size);
        var totalElements = irdataRepository.count();
        return new PagedResult<>(posts, totalElements, page, size);
    }
}
