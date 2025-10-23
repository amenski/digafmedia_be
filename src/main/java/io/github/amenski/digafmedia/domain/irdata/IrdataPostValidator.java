package io.github.amenski.digafmedia.domain.irdata;

import io.github.amenski.digafmedia.domain.Validator;

import java.math.BigDecimal;

public class IrdataPostValidator implements Validator<IrdataPost> {

    @Override
    public void validate(IrdataPost post) {
        if (post == null) {
            throw new IllegalArgumentException("Irdata post cannot be null");
        }
        if (post.title() == null || post.title().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (post.description() == null || post.description().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (post.contactName() == null || post.contactName().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be empty");
        }
        if (post.contactPhone() == null || post.contactPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact phone cannot be empty");
        }
        // Validate phone number format
        if (!isValidPhoneNumber(post.contactPhone())) {
            throw new IllegalArgumentException("Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }
        // Email is optional, but if provided must be valid
        if (post.contactEmail() != null && !post.contactEmail().trim().isEmpty() && !isValidEmail(post.contactEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Goal amount should be positive if provided
        if (post.goalAmount() != null && post.goalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Goal amount must be greater than zero");
        }
        // Current amount should not be negative
        if (post.currentAmount() != null && post.currentAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Current amount cannot be negative");
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
