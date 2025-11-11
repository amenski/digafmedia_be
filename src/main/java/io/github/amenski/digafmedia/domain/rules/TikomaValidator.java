package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;

public final class TikomaValidator {

    private TikomaValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validate(TikomaAlert alert) {
        ValidationResult result = new ValidationResult();
        
        if (alert == null) {
            result.addError("alert", "Tikoma alert cannot be null");
            return result;
        }
        
        if (alert.title() == null || alert.title().trim().isEmpty()) {
            result.addError("title", "Title cannot be empty");
        }
        
        if (alert.message() == null || alert.message().trim().isEmpty()) {
            result.addError("message", "Message cannot be empty");
        }
        
        // Contact name and phone are optional for community alerts
        // But if phone is provided, validate its format
        if (alert.contactPhone() != null && !alert.contactPhone().trim().isEmpty() && !ValidationUtils.isValidPhoneNumber(alert.contactPhone())) {
            result.addError("contactPhone", "Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }
        
        return result;
    }
}

