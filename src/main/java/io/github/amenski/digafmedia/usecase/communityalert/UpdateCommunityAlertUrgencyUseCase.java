package io.github.amenski.digafmedia.usecase.communityalert;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.alert.AlertUrgency;
import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

public class UpdateCommunityAlertUrgencyUseCase {

    private final CommunityAlertRepository communityAlertRepository;

    public UpdateCommunityAlertUrgencyUseCase(CommunityAlertRepository communityAlertRepository) {
        this.communityAlertRepository = communityAlertRepository;
    }

    public CommunityAlert invoke(Long id, AlertUrgency newUrgency, CurrentUser currentUser) {
        CommunityAlert alert = communityAlertRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Community alert not found"));

        CommunityAlert updatedAlert = alert.updateUrgency(newUrgency);
        return communityAlertRepository.save(updatedAlert);
    }
}
