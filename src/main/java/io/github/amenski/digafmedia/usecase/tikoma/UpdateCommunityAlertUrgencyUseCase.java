package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.alert.AlertUrgency;
import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

public class UpdateCommunityAlertUrgencyUseCase {

    private final CommunityAlertRepository tikomaRepository;

    public UpdateCommunityAlertUrgencyUseCase(CommunityAlertRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public CommunityAlert invoke(Long id, AlertUrgency newUrgency, CurrentUser currentUser) {
        CommunityAlert alert = tikomaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Community alert not found"));

        CommunityAlert updatedAlert = alert.updateUrgency(newUrgency);
        return tikomaRepository.save(updatedAlert);
    }
}
