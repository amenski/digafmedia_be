package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comments;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;

public class GetAllCommentsUseCase {

    private final CommentRepository commentRepository;

    public GetAllCommentsUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comments invoke() {
        return new Comments(commentRepository.findAll());
    }
}
