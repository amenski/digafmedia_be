package io.github.amenski.digafmedia.usecase.communityalert;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

public class DeleteCommunityAlertUseCase {

    private final CommunityAlertRepository communityAlertRepository;

    public DeleteCommunityAlertUseCase(CommunityAlertRepository communityAlertRepository) {
        this.communityAlertRepository = communityAlertRepository;
    }

    public void invoke(Long id, CurrentUser currentUser) {
        if (!communityAlertRepository.existsById(id)) {
            throw new RuntimeException("Community alert not found");
        }

        communityAlertRepository.deleteById(id);
    }
}
