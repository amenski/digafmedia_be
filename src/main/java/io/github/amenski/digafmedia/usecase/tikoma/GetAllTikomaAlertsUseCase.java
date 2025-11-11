package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;

public class GetAllTikomaAlertsUseCase {

    private final TikomaRepository tikomaRepository;

    public GetAllTikomaAlertsUseCase(TikomaRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public PagedResult<TikomaAlert> invoke(TikomaUrgency urgency, int page, int size) {
        if (urgency != null) {
            var alerts = tikomaRepository.findByUrgencyPaginated(urgency, page, size);
            var totalElements = tikomaRepository.countByUrgency(urgency);
            return new PagedResult<>(alerts, totalElements, page, size);
        }
        var alerts = tikomaRepository.findAllPaginated(page, size);
        var totalElements = tikomaRepository.count();
        return new PagedResult<>(alerts, totalElements, page, size);
    }
}
