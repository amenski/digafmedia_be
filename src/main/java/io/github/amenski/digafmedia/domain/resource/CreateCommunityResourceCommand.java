package io.github.amenski.digafmedia.domain.resource;

public record CreateCommunityResourceCommand(
    String serviceName,
    String providerName,
    String description,
    String location,
    String contactPhone,
    String contactEmail,
    String category,
    String hoursOfOperation
) {
    // Validation could be added here if needed
}
