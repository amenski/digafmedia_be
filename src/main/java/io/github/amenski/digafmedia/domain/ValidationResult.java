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
}
