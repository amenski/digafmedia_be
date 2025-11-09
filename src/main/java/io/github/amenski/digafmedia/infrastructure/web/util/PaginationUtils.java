package io.github.amenski.digafmedia.infrastructure.web.util;

import org.springframework.http.ResponseEntity;

public final class PaginationUtils {

    private PaginationUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Validates pagination parameters and returns an error response if invalid.
     * 
     * @param page the page number (0-based)
     * @param size the page size
     * @return ResponseEntity with error if validation fails, null if valid
     */
    public static ResponseEntity<?> validatePaginationParameters(int page, int size) {
        return validatePaginationParameters(page, size, Defaults.MAX_SIZE);
    }

    /**
     * Validates pagination parameters with custom maximum page size.
     * 
     * @param page the page number (0-based)
     * @param size the page size
     * @param maxSize maximum allowed page size
     * @return ResponseEntity with error if validation fails, null if valid
     */
    public static ResponseEntity<?> validatePaginationParameters(int page, int size, int maxSize) {
        if (page < 0) {
            return ResponseEntity.badRequest().body("Page must be non-negative");
        }
        if (size <= 0) {
            return ResponseEntity.badRequest().body("Size must be positive");
        }
        if (size > maxSize) {
            return ResponseEntity.badRequest().body("Page size cannot exceed " + maxSize);
        }
        return null;
    }

    /**
     * Default pagination constants for consistent behavior across the application.
     */
    public static final class Defaults {
        public static final int PAGE = 0;
        public static final int SIZE = 20;
        public static final int MAX_SIZE = 100;

        private Defaults() {
          //
        }
    }
}