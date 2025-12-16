package io.github.amenski.digafmedia.usecase.tikoma;

import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

import java.util.Optional;

public class GetCommunityAlertByIdUseCase {

    private final CommunityAlertRepository tikomaRepository;

    public GetCommunityAlertByIdUseCase(CommunityAlertRepository tikomaRepository) {
        this.tikomaRepository = tikomaRepository;
    }

    public Optional<CommunityAlert> invoke(Long id) {
        return tikomaRepository.findById(id);
    }
}
