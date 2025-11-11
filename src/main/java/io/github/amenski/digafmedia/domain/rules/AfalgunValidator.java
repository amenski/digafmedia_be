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
        
        if (command.missingPersonName() == null || command.missingPersonName().trim().isEmpty()) {
            result.addError("missingPersonName", "Missing person name cannot be empty");
        }
        
        if (command.contactName() == null || command.contactName().trim().isEmpty()) {
            result.addError("contactName", "Contact name cannot be empty");
        }
        
        if (command.contactPhone() == null || command.contactPhone().trim().isEmpty()) {
            result.addError("contactPhone", "Contact phone cannot be empty");
        }
        
        // Validate phone number format
        if (command.contactPhone() != null && !ValidationUtils.isValidPhoneNumber(command.contactPhone())) {
            result.addError("contactPhone", "Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }
        
        // Email is optional, but if provided must be valid
        if (command.contactEmail() != null && !command.contactEmail().trim().isEmpty() && !ValidationUtils.isValidEmail(command.contactEmail())) {
            result.addError("contactEmail", "Invalid email format");
        }
        
        return result;
    }
}

