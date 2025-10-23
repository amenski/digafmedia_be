package io.github.amenski.digafmedia.domain.afalgun;

import io.github.amenski.digafmedia.domain.Validator;

public class AfalgunPostValidator implements Validator<AfalgunPost> {

    @Override
    public void validate(AfalgunPost post) {
        if (post == null) {
            throw new IllegalArgumentException("Afalgun post cannot be null");
        }
        if (post.missingPersonName() == null || post.missingPersonName().trim().isEmpty()) {
            throw new IllegalArgumentException("Missing person name cannot be empty");
        }
        if (post.contactName() == null || post.contactName().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be empty");
        }
        if (post.contactPhone() == null || post.contactPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact phone cannot be empty");
        }
        // Validate phone number format (basic Ethiopian format: +251XXXXXXXXX or 09XXXXXXXX)
        if (!isValidPhoneNumber(post.contactPhone())) {
            throw new IllegalArgumentException("Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }
        // Email is optional, but if provided must be valid
        if (post.contactEmail() != null && !post.contactEmail().trim().isEmpty() && !isValidEmail(post.contactEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleanPhone = phone.trim();
        // Accept +251XXXXXXXXX (13 chars) or 09XXXXXXXX (10 chars starting with 09)
        return cleanPhone.matches("^\\+251\\d{9}$") || cleanPhone.matches("^09\\d{8}$");
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Basic email validation
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}