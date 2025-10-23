package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;
import java.time.OffsetDateTime;

public class CreateCommentUseCase {

    private final CommentRepository commentRepository;
    private final Validator<Comment> commentValidator;

    public CreateCommentUseCase(CommentRepository commentRepository, Validator<Comment> commentValidator) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
    }

    public Comment invoke(Comment comment) {
        // Set creation timestamp if missing and validate business rules
        Comment toPersist = comment;
        if (comment.createdAt() == null) {
            toPersist = new Comment(
                    comment.id(),
                    comment.name(),
                    comment.email(),
                    comment.content(),
                    OffsetDateTime.now()
            );
        }
        commentValidator.validate(toPersist);
        return commentRepository.save(toPersist);
    }
}
