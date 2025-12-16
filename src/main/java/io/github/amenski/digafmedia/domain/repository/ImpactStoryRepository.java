package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.story.ImpactStory;

import java.util.List;
import java.util.Optional;

public interface ImpactStoryRepository {

    List<ImpactStory> findAll();

    List<ImpactStory> findApproved();

    List<ImpactStory> findAllPaginated(int page, int size);

    List<ImpactStory> findByApprovedPaginated(boolean approved, int page, int size);

    List<ImpactStory> findApprovedPaginated(int page, int size);

    long count();

    long countByApproved(boolean approved);

    long countApproved();

    Optional<ImpactStory> findById(Long id);

    boolean existsById(Long id);

    ImpactStory save(ImpactStory story);

    void deleteById(Long id);
}
