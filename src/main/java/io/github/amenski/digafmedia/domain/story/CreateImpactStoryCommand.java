package io.github.amenski.digafmedia.domain.story;

public record CreateImpactStoryCommand(
    String title,
    String story,
    String authorName,
    String authorLocation
) {
    // Validation could be added here if needed
}
