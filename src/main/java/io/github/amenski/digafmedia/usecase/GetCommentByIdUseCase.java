package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetCommentByIdUseCase {

    private final CommentRepository commentRepository;

    public GetCommentByIdUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Comment> invoke(Long id) {
        return commentRepository.findById(id);
    }
}