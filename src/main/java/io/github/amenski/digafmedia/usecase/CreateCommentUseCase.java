package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCommentUseCase {

    private final CommentRepository commentRepository;

    public CreateCommentUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment invoke(Comment comment) {
        return commentRepository.save(comment);
    }
}