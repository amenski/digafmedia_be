package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;

public final class FreeServiceValidator {

    private FreeServiceValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validate(FreeService service) {
        ValidationResult result = new ValidationResult();
        
        if (service == null) {
            result.addError("service", "Free service cannot be null");
            return result;
        }
        
        if (service.serviceName() == null || service.serviceName().trim().isEmpty()) {
            result.addError("serviceName", "Service name cannot be empty");
        }
        
        if (service.providerName() == null || service.providerName().trim().isEmpty()) {
            result.addError("providerName", "Provider name cannot be empty");
        }
        
        // At least one contact method should be provided
        boolean hasPhone = service.contactPhone() != null && !service.contactPhone().trim().isEmpty();
        boolean hasEmail = service.contactEmail() != null && !service.contactEmail().trim().isEmpty();

        if (!hasPhone && !hasEmail) {
            result.addError("contact", "At least one contact method (phone or email) must be provided");
        }

        // Validate phone number format if provided
        if (hasPhone && !ValidationUtils.isValidPhoneNumber(service.contactPhone())) {
            result.addError("contactPhone", "Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }

        // Validate email format if provided
        if (hasEmail && !ValidationUtils.isValidEmail(service.contactEmail())) {
            result.addError("contactEmail", "Invalid email format");
        }
        
        return result;
    }
}

