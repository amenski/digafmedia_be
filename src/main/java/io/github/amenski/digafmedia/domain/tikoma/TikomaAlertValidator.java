package io.github.amenski.digafmedia.domain.tikoma;

import io.github.amenski.digafmedia.domain.Validator;

public class TikomaAlertValidator implements Validator<TikomaAlert> {

    @Override
    public void validate(TikomaAlert alert) {
        if (alert == null) {
            throw new IllegalArgumentException("Tikoma alert cannot be null");
        }
        if (alert.title() == null || alert.title().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (alert.message() == null || alert.message().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        // Contact name and phone are optional for community alerts
        if (alert.contactPhone() != null && !alert.contactPhone().trim().isEmpty() && !isValidPhoneNumber(alert.contactPhone())) {
            throw new IllegalArgumentException("Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX");
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleanPhone = phone.trim();
        return cleanPhone.matches("^\\+251\\d{9}$") || cleanPhone.matches("^09\\d{8}$");
    }
}
