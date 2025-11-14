package io.github.amenski.digafmedia.domain;

import java.util.Map;

public class DomainValidationException extends RuntimeException {
    private final Map<String, java.util.List<String>> errors;

    public DomainValidationException(String message) {
        super(message);
        this.errors = null;
    }

    public DomainValidationException(String message, Map<String, java.util.List<String>> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, java.util.List<String>> getErrors() {
        return errors;
    }
}

