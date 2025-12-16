package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.alert.AlertUrgency;

import java.util.List;
import java.util.Optional;

public interface CommunityAlertRepository {

    List<CommunityAlert> findAll();

    List<CommunityAlert> findRecent(int limit);

    List<CommunityAlert> findByUrgency(AlertUrgency urgency);

    List<CommunityAlert> findAllPaginated(int page, int size);

    List<CommunityAlert> findByUrgencyPaginated(AlertUrgency urgency, int page, int size);

    long count();

    long countByUrgency(AlertUrgency urgency);

    Optional<CommunityAlert> findById(Long id);

    boolean existsById(Long id);

    CommunityAlert save(CommunityAlert alert);

    void deleteById(Long id);
}
