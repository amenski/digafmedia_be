package io.github.amenski.digafmedia.domain.rules;

import static org.apache.commons.lang3.StringUtils.isBlank;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.DomainValidationException;

public final class CommentValidatorDefault implements Validator<Comment> {

    @Override
    public void validate(Comment comment) {
        if (comment == null) {
            throw new DomainValidationException("Comment must not be null");
        }
        if (isBlank(comment.name())) {
            throw new DomainValidationException("Name must not be blank");
        }
        if (isBlank(comment.email())) {
            throw new DomainValidationException("Email must not be blank");
        }
        if (isBlank(comment.content())) {
            throw new DomainValidationException("Content must not be blank");
        }
        if (!comment.email().contains("@")) {
            throw new DomainValidationException("Email format is invalid");
        }
    }
}

