package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;

import java.util.List;
import java.util.Optional;

public interface TikomaRepository {

    List<TikomaAlert> findAll();

    List<TikomaAlert> findRecent(int limit);

    List<TikomaAlert> findByUrgency(TikomaUrgency urgency);

    List<TikomaAlert> findAllPaginated(int page, int size);

    List<TikomaAlert> findByUrgencyPaginated(TikomaUrgency urgency, int page, int size);

    long count();

    long countByUrgency(TikomaUrgency urgency);

    Optional<TikomaAlert> findById(Long id);

    TikomaAlert save(TikomaAlert alert);

    void deleteById(Long id);
}
