package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.CommentRepository;
import io.github.amenski.digafmedia.domain.rules.CommentValidator;

public class CreateCommentUseCase {

    private final CommentRepository commentRepository;

    public CreateCommentUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment invoke(Comment comment) {
        var validationResult = CommentValidator.validate(comment);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        Comment toPersist = Comment.withDefaults(comment);
        return commentRepository.save(toPersist);
    }
}
