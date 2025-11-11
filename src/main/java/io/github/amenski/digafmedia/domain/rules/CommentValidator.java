package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.ValidationResult;

public final class CommentValidator {

    private CommentValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validate(Comment comment) {
        ValidationResult result = new ValidationResult();
        
        if (comment == null) {
            result.addError("comment", "Comment cannot be null");
            return result;
        }
        
        if (comment.name() == null || comment.name().trim().isEmpty()) {
            result.addError("name", "Comment name cannot be empty");
        }
        
        if (comment.email() == null || comment.email().trim().isEmpty()) {
            result.addError("email", "Comment email cannot be empty");
        }
        
        if (comment.content() == null || comment.content().trim().isEmpty()) {
            result.addError("content", "Comment content cannot be empty");
        }
        
        // Email format validation
        if (comment.email() != null && !comment.email().trim().isEmpty() && !ValidationUtils.isValidEmail(comment.email())) {
            result.addError("email", "Invalid email format");
        }
        
        return result;
    }
}

