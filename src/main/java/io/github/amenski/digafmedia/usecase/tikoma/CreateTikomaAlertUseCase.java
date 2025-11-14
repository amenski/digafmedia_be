package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;
import io.github.amenski.digafmedia.domain.rules.TikomaValidator;

public class CreateTikomaAlertUseCase {

    private final TikomaRepository tikomaRepository;

    public CreateTikomaAlertUseCase(TikomaRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public TikomaAlert invoke(TikomaAlert alert, CurrentUser currentUser) {
        TikomaAlert toPersist = TikomaAlert.withDefaults(alert, currentUser.id());
        
        var validationResult = TikomaValidator.validate(toPersist);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        return tikomaRepository.save(toPersist);
    }
}
