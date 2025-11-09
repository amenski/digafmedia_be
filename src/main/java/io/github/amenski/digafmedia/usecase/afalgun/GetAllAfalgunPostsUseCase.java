package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPosts;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class GetAllAfalgunPostsUseCase {

    private final AfalgunRepository afalgunRepository;

    public GetAllAfalgunPostsUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public AfalgunPosts invoke(AfalgunStatus status, Integer page, Integer size) {
        if (page != null && size != null) {
            // Use paginated methods
            if (status == null) {
                var posts = afalgunRepository.findAllPaginated(page, size);
                var total = afalgunRepository.count();
                return AfalgunPosts.of(posts, page, size, total);
            } else {
                var posts = afalgunRepository.findByStatusPaginated(status, page, size);
                var total = afalgunRepository.countByStatus(status);
                return AfalgunPosts.of(posts, page, size, total);
            }
        } else {
            // Use non-paginated methods
            if (status == null) {
                return new AfalgunPosts(afalgunRepository.findAll());
            }
            return new AfalgunPosts(afalgunRepository.findByStatus(status));
        }
    }
}
