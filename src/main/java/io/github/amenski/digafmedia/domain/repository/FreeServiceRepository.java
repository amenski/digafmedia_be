package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.freeservice.FreeService;

import java.util.List;
import java.util.Optional;

public interface FreeServiceRepository {

    List<FreeService> findAll();

    List<FreeService> findByActive(Boolean isActive);

    List<FreeService> findByCategory(String category);

    List<FreeService> findByLocationContaining(String location);

    Optional<FreeService> findById(Long id);

    FreeService save(FreeService service);

    void deleteById(Long id);
}
