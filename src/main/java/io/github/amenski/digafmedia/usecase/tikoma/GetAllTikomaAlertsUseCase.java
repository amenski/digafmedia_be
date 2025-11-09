package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.tikoma.TikomaAlerts;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;

public class GetAllTikomaAlertsUseCase {

    private final TikomaRepository tikomaRepository;

    public GetAllTikomaAlertsUseCase(TikomaRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public TikomaAlerts invoke(TikomaUrgency urgency, Integer page, Integer size, Integer limit) {
        if (page != null && size != null) {
            if (urgency == null) {
                var alerts = tikomaRepository.findAllPaginated(page, size);
                var total = tikomaRepository.count();
                return TikomaAlerts.of(alerts, page, size, total);
            } else {
                var alerts = tikomaRepository.findByUrgencyPaginated(urgency, page, size);
                var total = tikomaRepository.countByUrgency(urgency);
                return TikomaAlerts.of(alerts, page, size, total);
            }
        } else {
            // Use non-paginated methods
            if (limit != null && limit > 0) {
                return new TikomaAlerts(tikomaRepository.findRecent(limit));
            } else if (urgency != null) {
                return new TikomaAlerts(tikomaRepository.findByUrgency(urgency));
            } else {
                return new TikomaAlerts(tikomaRepository.findAll());
            }
        }
    }
}
