package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comments;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;

public class GetAllCommentsUseCase {

    private final CommentRepository commentRepository;

    public GetAllCommentsUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comments invoke(Integer page, Integer size) {
        if (page != null && size != null) {
            var comments = commentRepository.findAllPaginated(page, size);
            var total = commentRepository.count();
            return Comments.of(comments, page, size, total);
        } else {
            return new Comments(commentRepository.findAll());
        }
    }
}
