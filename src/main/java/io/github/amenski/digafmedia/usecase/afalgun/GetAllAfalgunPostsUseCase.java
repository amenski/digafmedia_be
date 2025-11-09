package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.afalgun.SearchAfalgunPostsCommand;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class GetAllAfalgunPostsUseCase {

    private final AfalgunRepository afalgunRepository;

    public GetAllAfalgunPostsUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public PagedResult<AfalgunPost> execute(SearchAfalgunPostsCommand command) {
        // For now, we'll use the existing repository methods
        // In a real implementation, we would add repository methods that support all search criteria
        
        if (command.status() == null && command.query() == null && command.location() == null) {
            var posts = afalgunRepository.findAllPaginated(command.page(), command.size());
            var total = afalgunRepository.count();
            return new PagedResult<>(posts, total, command.page(), command.size());
        } else if (command.status() != null && command.query() == null && command.location() == null) {
            var posts = afalgunRepository.findByStatusPaginated(command.status(), command.page(), command.size());
            var total = afalgunRepository.countByStatus(command.status());
            return new PagedResult<>(posts, total, command.page(), command.size());
        } else {
            // Fallback to basic pagination when complex search criteria are provided
            // This would need to be enhanced with proper search repository methods
            var posts = afalgunRepository.findAllPaginated(command.page(), command.size());
            var total = afalgunRepository.count();
            return new PagedResult<>(posts, total, command.page(), command.size());
        }
    }
}
