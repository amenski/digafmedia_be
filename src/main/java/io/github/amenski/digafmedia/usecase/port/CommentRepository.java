package io.github.amenski.digafmedia.usecase.port;

import io.github.amenski.digafmedia.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}

