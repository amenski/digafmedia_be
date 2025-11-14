package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.Pagination;

import java.util.List;
import java.util.Optional;

public interface AfalgunRepository {

    List<AfalgunPost> findAll();

    List<AfalgunPost> findByStatus(AfalgunStatus status);

    List<AfalgunPost> findAllPaginated(int page, int size);

    List<AfalgunPost> findByStatusPaginated(AfalgunStatus status, int page, int size);

    long count();

    long countByStatus(AfalgunStatus status);

    Optional<AfalgunPost> findById(Long id);

    boolean existsById(Long id);

    AfalgunPost save(AfalgunPost post);

    void deleteById(Long id);
}
