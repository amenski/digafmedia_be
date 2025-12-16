package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.alert.CreateCommunityAlertCommand;

public final class CommunityAlertValidator {

    private CommunityAlertValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validateCreateCommand(CreateCommunityAlertCommand command) {
        ValidationResult result = new ValidationResult();
        
        if (command == null) {
            result.addError("command", "Community alert command cannot be null");
            return result;
        }
        
        // Required field validation
        validateRequiredField(result, "title", command.title(), "Alert title cannot be empty");
        validateRequiredField(result, "message", command.message(), "Alert message cannot be empty");
        
        // Field length validation
        validateFieldLength(result, "title", command.title(), 5, 200, "Title must be between 5 and 200 characters");
        validateFieldLength(result, "message", command.message(), 10, 1000, "Message must be between 10 and 1000 characters");
        validateFieldLength(result, "contactName", command.contactName(), 2, 50, "Contact name must be between 2 and 50 characters");
        
        // Phone number format validation (optional)
        if (command.contactPhone() != null && !command.contactPhone().trim().isEmpty()) {
            if (!ValidationUtils.isValidPhoneNumber(command.contactPhone())) {
                result.addError("contactPhone", "Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
            }
        }
        
        // Email validation (optional field)
        if (command.contactEmail() != null && !command.contactEmail().trim().isEmpty()) {
            if (!ValidationUtils.isValidEmail(command.contactEmail())) {
                result.addError("contactEmail", "Invalid email format");
            }
            validateFieldLength(result, "contactEmail", command.contactEmail(), 5, 100, "Email must be between 5 and 100 characters");
        }
        
        return result;
    }

    private static void validateRequiredField(ValidationResult result, String fieldName, String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            result.addError(fieldName, errorMessage);
        }
    }

    private static void validateFieldLength(ValidationResult result, String fieldName, String value, int minLength, int maxLength, String errorMessage) {
        if (value != null && (value.length() < minLength || value.length() > maxLength)) {
            result.addError(fieldName, errorMessage);
        }
    }
}
