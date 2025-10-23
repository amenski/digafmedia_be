package io.github.amenski.digafmedia.domain.freeservice;

import io.github.amenski.digafmedia.domain.Validator;

public class FreeServiceValidator implements Validator<FreeService> {

    @Override
    public void validate(FreeService service) {
        if (service == null) {
            throw new IllegalArgumentException("Free service cannot be null");
        }
        if (service.serviceName() == null || service.serviceName().trim().isEmpty()) {
            throw new IllegalArgumentException("Service name cannot be empty");
        }
        if (service.providerName() == null || service.providerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name cannot be empty");
        }
        // At least one contact method should be provided
        boolean hasPhone = service.contactPhone() != null && !service.contactPhone().trim().isEmpty();
        boolean hasEmail = service.contactEmail() != null && !service.contactEmail().trim().isEmpty();

        if (!hasPhone && !hasEmail) {
            throw new IllegalArgumentException("At least one contact method (phone or email) must be provided");
        }

        // Validate phone number format if provided
        if (hasPhone && !isValidPhoneNumber(service.contactPhone())) {
            throw new IllegalArgumentException("Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }

        // Validate email format if provided
        if (hasEmail && !isValidEmail(service.contactEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleanPhone = phone.trim();
        return cleanPhone.matches("^\\+251\\d{9}$") || cleanPhone.matches("^09\\d{8}$");
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
