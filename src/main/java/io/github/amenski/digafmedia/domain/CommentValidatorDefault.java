package io.github.amenski.digafmedia.domain;

public class CommentValidatorDefault implements Validator<Comment> {

    @Override
    public void validate(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        if (comment.name() == null || comment.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment name cannot be empty");
        }
        if (comment.email() == null || comment.email().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment email cannot be empty");
        }
        if (comment.content() == null || comment.content().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }
    }
}
