package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;

import java.util.List;
import java.util.Optional;

public interface IrdataRepository {

    List<IrdataPost> findRecent(int limit);

    List<IrdataPost> findRecentByStatus(IrdataStatus status, int limit);

    List<IrdataPost> findAllPaginated(int page, int size);

    List<IrdataPost> findByStatusPaginated(IrdataStatus status, int page, int size);

    long count();

    long countByStatus(IrdataStatus status);

    Optional<IrdataPost> findById(Long id);

    boolean existsById(Long id);

    IrdataPost save(IrdataPost post);

    void deleteById(Long id);
}
