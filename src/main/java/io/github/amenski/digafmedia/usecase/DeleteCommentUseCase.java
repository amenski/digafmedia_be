package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCommentUseCase {

    private final CommentRepository commentRepository;

    public DeleteCommentUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void invoke(Long id) {
        commentRepository.deleteById(id);
    }
}