package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class GetAllAfalgunPostsUseCase {

    private final AfalgunRepository afalgunRepository;

    public GetAllAfalgunPostsUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public PagedResult<AfalgunPost> invoke(AfalgunStatus status, String query, String location, int page, int size) {
        // For now, we'll use the existing repository methods
        // In a real implementation, we would add repository methods that support all search criteria
        
        if (status == null && query == null && location == null) {
            var posts = afalgunRepository.findAllPaginated(page, size);
            var totalElements = afalgunRepository.count();
            return new PagedResult<>(posts, totalElements, page, size);
        } else if (status != null && query == null && location == null) {
            var posts = afalgunRepository.findByStatusPaginated(status, page, size);
            var totalElements = afalgunRepository.countByStatus(status);
            return new PagedResult<>(posts, totalElements, page, size);
        } else {
            // Fallback to all posts when complex search criteria are provided
            // This would need to be enhanced with proper search repository methods
            var posts = afalgunRepository.findAllPaginated(page, size);
            var totalElements = afalgunRepository.count();
            return new PagedResult<>(posts, totalElements, page, size);
        }
    }
}
