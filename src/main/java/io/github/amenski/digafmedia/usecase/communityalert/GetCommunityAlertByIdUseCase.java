package io.github.amenski.digafmedia.usecase.communityalert;

import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;

import java.util.Optional;

public class GetCommunityAlertByIdUseCase {

    private final CommunityAlertRepository communityAlertRepository;

    public GetCommunityAlertByIdUseCase(CommunityAlertRepository communityAlertRepository) {
        this.communityAlertRepository = communityAlertRepository;
    }

    public Optional<CommunityAlert> invoke(Long id) {
        return communityAlertRepository.findById(id);
    }
}
