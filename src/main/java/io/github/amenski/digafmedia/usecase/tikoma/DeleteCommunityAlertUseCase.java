package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

public class DeleteCommunityAlertUseCase {

    private final CommunityAlertRepository tikomaRepository;

    public DeleteCommunityAlertUseCase(CommunityAlertRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public void invoke(Long id, CurrentUser currentUser) {
        if (!tikomaRepository.existsById(id)) {
            throw new RuntimeException("Community alert not found");
        }

        tikomaRepository.deleteById(id);
    }
}
