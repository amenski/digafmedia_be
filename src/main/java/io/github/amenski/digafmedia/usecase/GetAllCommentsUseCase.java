package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;

public class GetAllCommentsUseCase {

    private final CommentRepository commentRepository;

    public GetAllCommentsUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public PagedResult<Comment> invoke(int page, int size) {
        var comments = commentRepository.findAllPaginated(page, size);
        var totalElements = commentRepository.count();
        return new PagedResult<>(comments, totalElements, page, size);
    }
}
