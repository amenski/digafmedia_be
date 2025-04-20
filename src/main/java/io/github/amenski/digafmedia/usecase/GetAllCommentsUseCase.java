package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comments;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetAllCommentsUseCase {

    private final CommentRepository commentRepository;

    public GetAllCommentsUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public Comments invoke() {
        return new Comments(commentRepository.findAll());
    }
}