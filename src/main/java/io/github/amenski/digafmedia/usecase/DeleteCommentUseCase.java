package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.repository.CommentRepository;

public class DeleteCommentUseCase {

    private final CommentRepository commentRepository;

    public DeleteCommentUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void invoke(Long id) {
        commentRepository.deleteById(id);
    }
}
