package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.resource.CreateCommunityResourceCommand;

public final class CommunityResourceValidator {

    private CommunityResourceValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validateCreateCommand(CreateCommunityResourceCommand command) {
        if (command == null) {
            return ValidationResult.validate(command)
                    .notNull("command", "Community resource command cannot be null");
        }

        return ValidationResult.validate(command)
                .notEmpty("serviceName", command.serviceName(), "Service name cannot be empty")
                .notEmpty("providerName", command.providerName(), "Provider name cannot be empty")
                .length("serviceName", command.serviceName(), 2, 200, "Service name must be between 2 and 200 characters")
                .length("providerName", command.providerName(), 2, 100, "Provider name must be between 2 and 100 characters")
                .length("description", command.description(), 10, 1000, "Description must be between 10 and 1000 characters")
                .length("location", command.location(), 5, 200, "Location must be between 5 and 200 characters")
                .length("category", command.category(), 2, 100, "Category must be between 2 and 100 characters")
                .length("hoursOfOperation", command.hoursOfOperation(), 5, 100, "Hours of operation must be between 5 and 100 characters")
                .phone("contactPhone", command.contactPhone(), "Invalid phone number format. Expected: +251XXXXXXXXX or 09XXXXXXXX")
                .email("contactEmail", command.contactEmail(), "Invalid email format")
                .length("contactEmail", command.contactEmail(), 5, 100, "Email must be between 5 and 100 characters");
    }


}
