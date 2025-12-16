package io.github.amenski.digafmedia.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {
    private final Map<String, List<String>> errors = new HashMap<>();

    public void addError(String field, String message) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !isValid();
    }

    public List<String> getFieldErrors(String field) {
        return errors.getOrDefault(field, new ArrayList<>());
    }

    public void merge(ValidationResult other) {
        if (other != null && other.hasErrors()) {
            other.errors.forEach(
                    (field, fieldErrors) -> fieldErrors.forEach(error -> addError(field, error)));
        }
    }

    public static ValidationResult error(String field, String message) {
        ValidationResult result = new ValidationResult();
        result.addError(field, message);
        return result;
    }

    // Validation Chain Pattern Methods

    public static ValidationResult validate(Object command) {
        return new ValidationResult();
    }

    public ValidationResult notNull(String field, String message) {
        addError(field, message);
        return this;
    }

    public ValidationResult notEmpty(String field, String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            addError(field, message);
        }
        return this;
    }

    public ValidationResult length(String field, String value, int minLength, int maxLength, String message) {
        if (value != null && (value.length() < minLength || value.length() > maxLength)) {
            addError(field, message);
        }
        return this;
    }

    public ValidationResult minLength(String field, String value, int minLength, String message) {
        if (value != null && value.length() < minLength) {
            addError(field, message);
        }
        return this;
    }

    public ValidationResult maxLength(String field, String value, int maxLength, String message) {
        if (value != null && value.length() > maxLength) {
            addError(field, message);
        }
        return this;
    }

    public ValidationResult email(String field, String value, String message) {
        if (value != null && !value.trim().isEmpty() && !isValidEmail(value)) {
            addError(field, message);
        }
        return this;
    }

    public ValidationResult phone(String field, String value, String message) {
        if (value != null && !value.trim().isEmpty() && !isValidPhoneNumber(value)) {
            addError(field, message);
        }
        return this;
    }

    public ValidationResult custom(String field, boolean condition, String message) {
        if (condition) {
            addError(field, message);
        }
        return this;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidPhoneNumber(String phone) {
        // Basic phone validation - can be enhanced
        return phone.matches("\\+?[0-9]{9,15}");
    }
}
