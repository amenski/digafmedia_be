package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;

public class CreateTikomaAlertUseCase {

    private final TikomaRepository tikomaRepository;
    private final Validator<TikomaAlert> tikomaAlertValidator;

    public CreateTikomaAlertUseCase(TikomaRepository tikomaRepository, Validator<TikomaAlert> tikomaAlertValidator) {
        this.tikomaRepository = tikomaRepository;
        this.tikomaAlertValidator = tikomaAlertValidator;
    }

    public TikomaAlert invoke(TikomaAlert alert) {
        // Set default urgency if missing
        TikomaAlert toPersist = alert;
        if (alert.urgency() == null) {
            toPersist = new TikomaAlert(
                    alert.id(),
                    alert.title(),
                    alert.message(),
                    alert.contactName(),
                    alert.contactPhone(),
                    TikomaUrgency.MEDIUM,
                    alert.createdAt(),
                    alert.modifiedAt()
            );
        }
        tikomaAlertValidator.validate(toPersist);
        return tikomaRepository.save(toPersist);
    }
}
