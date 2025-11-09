package io.github.amenski.digafmedia.infrastructure.web.mapper;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.infrastructure.web.model.CommentResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateCommentRequest;
import org.springframework.stereotype.Component;

@Component
public class CommentWebMapper {

    public Comment create(CreateCommentRequest request) {
        return new Comment(
                null,
                request.getName(),
                request.getEmail(),
                request.getContent(),
                null
        );
    }

    public CommentResponse toResponse(Comment comment) {
        return CommentResponse.fromComment(comment);
    }
}
