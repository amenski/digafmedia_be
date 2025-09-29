package io.github.amenski.digafmedia.domain.rules;

/**
 * Generic domain validation contract. Implementations must be side‑effect free
 * and throw DomainValidationException when the input breaks business rules.
 */
public interface Validator<T> {
    void validate(T input);
}

