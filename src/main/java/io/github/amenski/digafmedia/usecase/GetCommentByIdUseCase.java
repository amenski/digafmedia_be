package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;

public class GetCommentByIdUseCase {

    private final CommentRepository commentRepository;

    public GetCommentByIdUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment invoke(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.forEntity("Comment", id));
    }
}
