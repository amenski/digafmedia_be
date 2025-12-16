package io.github.amenski.digafmedia.domain.alert;

public record CreateCommunityAlertCommand(
    String title,
    String message,
    String contactName,
    String contactPhone,
    String contactEmail,
    AlertUrgency urgency
) {
    // Validation could be added here if needed
}
