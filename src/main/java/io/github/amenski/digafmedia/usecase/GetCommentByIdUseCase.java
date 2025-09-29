package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.usecase.port.CommentRepository;

import java.util.Optional;

public class GetCommentByIdUseCase {

    private final CommentRepository commentRepository;

    public GetCommentByIdUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> invoke(Long id) {
        return commentRepository.findById(id);
    }
}
