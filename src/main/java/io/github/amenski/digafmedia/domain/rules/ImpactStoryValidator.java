package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.story.CreateImpactStoryCommand;

public final class ImpactStoryValidator {

    private ImpactStoryValidator() {
        // Utility class - prevent instantiation
    }

    public static ValidationResult validateCreateCommand(CreateImpactStoryCommand command) {
        if (command == null) {
            return ValidationResult.validate(command)
                    .notNull("command", "Impact story command cannot be null");
        }

        return ValidationResult.validate(command)
                .notEmpty("title", command.title(), "Story title cannot be empty")
                .notEmpty("story", command.story(), "Story content cannot be empty")
                .length("title", command.title(), 5, 200, "Title must be between 5 and 200 characters")
                .length("story", command.story(), 20, 2000, "Story must be between 20 and 2000 characters")
                .length("authorName", command.authorName(), 2, 100, "Author name must be between 2 and 100 characters")
                .length("authorLocation", command.authorLocation(), 2, 100, "Author location must be between 2 and 100 characters");
    }


}
