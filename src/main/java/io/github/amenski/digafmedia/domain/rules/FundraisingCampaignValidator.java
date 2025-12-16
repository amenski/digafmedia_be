package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.fundraising.CreateFundraisingCampaignCommand;

public final class FundraisingCampaignValidator {

    private FundraisingCampaignValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validateCreateCommand(CreateFundraisingCampaignCommand command) {
        ValidationResult result = new ValidationResult();
        
        if (command == null) {
            result.addError("command", "Fundraising campaign command cannot be null");
            return result;
        }
        
        // Required field validation
        validateRequiredField(result, "title", command.title(), "Campaign title cannot be empty");
        validateRequiredField(result, "description", command.description(), "Campaign description cannot be empty");
        validateRequiredField(result, "goalAmount", command.goalAmount(), "Goal amount cannot be empty");
        validateRequiredField(result, "contactName", command.contactName(), "Contact name cannot be empty");
        validateRequiredField(result, "contactPhone", command.contactPhone(), "Contact phone cannot be empty");
        
        // Field length validation
        validateFieldLength(result, "title", command.title(), 5, 200, "Title must be between 5 and 200 characters");
        validateFieldLength(result, "description", command.description(), 20, 2000, "Description must be between 20 and 2000 characters");
        validateFieldLength(result, "contactName", command.contactName(), 2, 50, "Contact name must be between 2 and 50 characters");
        validateFieldLength(result, "bankName", command.bankName(), 2, 100, "Bank name must be between 2 and 100 characters");
        validateFieldLength(result, "accountNumber", command.accountNumber(), 5, 50, "Account number must be between 5 and 50 characters");
        validateFieldLength(result, "accountHolder", command.accountHolder(), 2, 100, "Account holder name must be between 2 and 100 characters");
        
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
        
        // Amount validation
        if (command.goalAmount() != null) {
            if (command.goalAmount().compareTo(java.math.BigDecimal.ZERO) < 0) {
                result.addError("goalAmount", "Goal amount cannot be negative");
            }
        }
        
        // Account number validation (if provided)
        if (command.accountNumber() != null && !command.accountNumber().trim().isEmpty()) {
            if (!command.accountNumber().matches("\\d+")) {
                result.addError("accountNumber", "Account number must contain only digits");
            }
        }
        
        return result;
    }

    private static void validateRequiredField(ValidationResult result, String fieldName, String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            result.addError(fieldName, errorMessage);
        }
    }
    
    private static void validateRequiredField(ValidationResult result, String fieldName, Object value, String errorMessage) {
        if (value == null) {
            result.addError(fieldName, errorMessage);
        }
    }

    private static void validateFieldLength(ValidationResult result, String fieldName, String value, int minLength, int maxLength, String errorMessage) {
        if (value != null && (value.length() < minLength || value.length() > maxLength)) {
            result.addError(fieldName, errorMessage);
        }
    }
}
