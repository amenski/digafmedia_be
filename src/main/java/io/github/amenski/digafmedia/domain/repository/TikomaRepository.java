package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;

import java.util.List;
import java.util.Optional;

public interface TikomaRepository {

    List<TikomaAlert> findAll();

    List<TikomaAlert> findRecent(int limit);

    Optional<TikomaAlert> findById(Long id);

    TikomaAlert save(TikomaAlert alert);

    void deleteById(Long id);
}
