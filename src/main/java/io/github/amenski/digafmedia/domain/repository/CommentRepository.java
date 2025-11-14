package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAll();

    List<Comment> findAllPaginated(int page, int size);

    long count();

    Optional<Comment> findById(Long id);

    boolean existsById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}

