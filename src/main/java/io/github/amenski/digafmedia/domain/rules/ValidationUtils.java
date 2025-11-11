package io.github.amenski.digafmedia.domain.rules;

import org.apache.commons.lang3.StringUtils;

/**
 * Common validation utility methods used across domain validators.
 * This class provides reusable validation logic to avoid duplication.
 */
public final class ValidationUtils {

    private ValidationUtils() {
        //
    }

    /**
     * Validates Ethiopian phone number format.
     * Accepts formats: +251XXXXXXXXX (13 chars) or 09XXXXXXXX (10 chars starting with 09)
     *
     * @param phone the phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        String cleanPhone = phone.trim();
        return cleanPhone.matches("^\\+251\\d{9}$") || cleanPhone.matches("^09\\d{8}$");
    }

    /**
     * Validates email format using a basic regex pattern.
     *
     * @param email the email address to validate
     * @return true if the email format is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}

