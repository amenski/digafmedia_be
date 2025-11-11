package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;

public class DeleteCommentUseCase {

    private final CommentRepository commentRepository;

    public DeleteCommentUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void invoke(Long id) {
        // Check if entity exists before deletion
        if (!commentRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("Comment", id);
        }
        commentRepository.deleteById(id);
    }
}
