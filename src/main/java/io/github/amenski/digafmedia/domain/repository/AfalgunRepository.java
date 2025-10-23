package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;

import java.util.List;
import java.util.Optional;

public interface AfalgunRepository {

    List<AfalgunPost> findAll();

    List<AfalgunPost> findByStatus(AfalgunStatus status);

    Optional<AfalgunPost> findById(Long id);

    AfalgunPost save(AfalgunPost post);

    void deleteById(Long id);
}
