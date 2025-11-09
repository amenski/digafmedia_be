package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;

public class DeleteAfalgunPostUseCase {

    private final AfalgunRepository afalgunRepository;

    public DeleteAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public void execute(Long id, CurrentUserAdapter currentUser) {
        afalgunRepository.deleteById(id);
    }
}
