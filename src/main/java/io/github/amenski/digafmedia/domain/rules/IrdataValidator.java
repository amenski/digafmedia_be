package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;

import java.math.BigDecimal;

public final class IrdataValidator {

    private IrdataValidator() {
        //
    }

    public static ValidationResult validate(IrdataPost post) {
        ValidationResult result = new ValidationResult();
        
        if (post == null) {
            result.addError("post", "Irdata post cannot be null");
            return result;
        }
        
        if (post.title() == null || post.title().trim().isEmpty()) {
            result.addError("title", "Title cannot be empty");
        }
        
        if (post.description() == null || post.description().trim().isEmpty()) {
            result.addError("description", "Description cannot be empty");
        }
        
        if (post.contactName() == null || post.contactName().trim().isEmpty()) {
            result.addError("contactName", "Contact name cannot be empty");
        }
        
        if (post.contactPhone() == null || post.contactPhone().trim().isEmpty()) {
            result.addError("contactPhone", "Contact phone cannot be empty");
        }
        
        // Validate phone number format
        if (!ValidationUtils.isValidPhoneNumber(post.contactPhone())) {
            result.addError("contactPhone", "Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }
        
        // Email is optional, but if provided must be valid
        if (!ValidationUtils.isValidEmail(post.contactEmail())) {
            result.addError("contactEmail", "Invalid email format");
        }
        
        // Goal amount should be positive if provided
        if (post.goalAmount() != null && post.goalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            result.addError("goalAmount", "Goal amount must be greater than zero");
        }
        
        // Current amount should not be negative
        if (post.currentAmount() != null && post.currentAmount().compareTo(BigDecimal.ZERO) < 0) {
            result.addError("currentAmount", "Current amount cannot be negative");
        }
        
        return result;
    }
}

