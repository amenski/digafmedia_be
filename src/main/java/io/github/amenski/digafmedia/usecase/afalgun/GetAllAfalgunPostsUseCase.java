package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPosts;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class GetAllAfalgunPostsUseCase {

    private final AfalgunRepository afalgunRepository;

    public GetAllAfalgunPostsUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public AfalgunPosts invoke(AfalgunStatus status) {
        if (status == null) {
            return new AfalgunPosts(afalgunRepository.findAll());
        }
        return new AfalgunPosts(afalgunRepository.findByStatus(status));
    }
}
