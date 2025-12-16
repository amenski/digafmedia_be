package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.resource.CommunityResource;

import java.util.List;
import java.util.Optional;

public interface CommunityResourceRepository {

    List<CommunityResource> findAll();

    List<CommunityResource> findByActive(Boolean isActive);

    List<CommunityResource> findByCategory(String category);

    List<CommunityResource> findByLocationContaining(String location);

    List<CommunityResource> findAllPaginated(int page, int size);

    List<CommunityResource> findByActivePaginated(Boolean isActive, int page, int size);

    long count();

    long countByActive(Boolean isActive);

    Optional<CommunityResource> findById(Long id);

    boolean existsById(Long id);

    CommunityResource save(CommunityResource resource);

    void deleteById(Long id);
}
