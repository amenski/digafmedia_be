package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

public class DeleteAfalgunPostUseCase {

    private final AfalgunRepository afalgunRepository;

    public DeleteAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public void invoke(Long id) {
        afalgunRepository.deleteById(id);
    }
}
