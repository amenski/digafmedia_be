package io.github.amenski.digafmedia.usecase.afalgun;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;

import java.util.Optional;

public class GetAfalgunPostByIdUseCase {

    private final AfalgunRepository afalgunRepository;

    public GetAfalgunPostByIdUseCase(AfalgunRepository afalgunRepository) {
        this.afalgunRepository = afalgunRepository;
    }

    public Optional<AfalgunPost> execute(Long id) {
        return afalgunRepository.findById(id);
    }
}
