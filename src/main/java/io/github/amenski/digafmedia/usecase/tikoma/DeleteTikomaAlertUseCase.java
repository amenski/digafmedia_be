package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;

public class DeleteTikomaAlertUseCase {

    private final TikomaRepository tikomaRepository;

    public DeleteTikomaAlertUseCase(TikomaRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public void invoke(Long id) {
        // Check if entity exists before deletion
        if (!tikomaRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("TikomaAlert", id);
        }
        tikomaRepository.deleteById(id);
    }
}
