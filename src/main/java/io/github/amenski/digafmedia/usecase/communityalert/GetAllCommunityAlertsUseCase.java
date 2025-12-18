package io.github.amenski.digafmedia.usecase.communityalert;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.alert.AlertUrgency;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

public class GetAllCommunityAlertsUseCase {

    private final CommunityAlertRepository communityAlertRepository;

    public GetAllCommunityAlertsUseCase(CommunityAlertRepository communityAlertRepository) {
        this.communityAlertRepository = communityAlertRepository;
    }

    public PagedResult<CommunityAlert> invoke(AlertUrgency urgency, int page, int size) {
        if (urgency != null) {
            var alerts = communityAlertRepository.findByUrgencyPaginated(urgency, page, size);
            var totalElements = communityAlertRepository.countByUrgency(urgency);
            return new PagedResult<>(alerts, totalElements, page, size);
        } else {
            var alerts = communityAlertRepository.findAllPaginated(page, size);
            var totalElements = communityAlertRepository.count();
            return new PagedResult<>(alerts, totalElements, page, size);
        }
    }
}
