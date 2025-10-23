package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.tikoma.TikomaAlerts;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;

public class GetAllTikomaAlertsUseCase {

    private final TikomaRepository tikomaRepository;

    public GetAllTikomaAlertsUseCase(TikomaRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public TikomaAlerts invoke(Integer limit) {
        if (limit != null && limit > 0) {
            return new TikomaAlerts(tikomaRepository.findRecent(limit));
        }
        return new TikomaAlerts(tikomaRepository.findAll());
    }
}
