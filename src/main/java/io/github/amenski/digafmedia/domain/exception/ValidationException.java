package io.github.amenski.digafmedia.domain.exception;

import io.github.amenski.digafmedia.domain.ValidationResult;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = Map.of();
    }

    public ValidationException(String message, Map<String, List<String>> errors) {
        super(message);
        this.errors = errors != null
            ? errors
            : Map.of();
    }

    public ValidationException(Map<String, List<String>> errors) {
        super("Validation failed");
        this.errors = errors != null
            ? errors
            : Map.of();
    }

    public ValidationException(ValidationResult validationResult) {
        super("Validation failed");
        this.errors = validationResult != null
            ? validationResult.getErrors()
            : Map.of();
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public boolean hasFieldErrors() {
        return !errors.isEmpty();
    }
}