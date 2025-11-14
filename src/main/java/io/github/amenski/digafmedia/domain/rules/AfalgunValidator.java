package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.afalgun.CreateAfalgunPostCommand;

public final class AfalgunValidator {

    private AfalgunValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validateCreateCommand(CreateAfalgunPostCommand command) {
        ValidationResult result = new ValidationResult();
        
        if (command == null) {
            result.addError("command", "Afalgun post command cannot be null");
            return result;
        }
        
        // Required field validation
        validateRequiredField(result, "missingPersonName", command.missingPersonName(), "Missing person name cannot be empty");
        validateRequiredField(result, "contactName", command.contactName(), "Contact name cannot be empty");
        validateRequiredField(result, "contactPhone", command.contactPhone(), "Contact phone cannot be empty");
        validateRequiredField(result, "lastSeenLocation", command.lastSeenLocation(), "Last seen location cannot be empty");
        
        // Field length validation
        validateFieldLength(result, "missingPersonName", command.missingPersonName(), 2, 100, "Missing person name must be between 2 and 100 characters");
        validateFieldLength(result, "contactName", command.contactName(), 2, 50, "Contact name must be between 2 and 50 characters");
        validateFieldLength(result, "lastSeenLocation", command.lastSeenLocation(), 5, 200, "Last seen location must be between 5 and 200 characters");
        validateFieldLength(result, "description", command.description(), 10, 1000, "Description must be between 10 and 1000 characters");
        
        // Phone number format validation
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
        
        // Age validation
        if (command.age() != null) {
            if (command.age() < 0 || command.age() > 150) {
                result.addError("age", "Age must be between 0 and 150");
            }
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

