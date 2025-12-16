package io.github.amenski.digafmedia.domain.missingperson;

public record CreateMissingPersonReportCommand(
    String missingPersonName,
    Integer age,
    String lastSeenLocation,
    String contactName,
    String contactPhone,
    String contactEmail,
    String description,
    ReportStatus status
) {
    public CreateMissingPersonReportCommand {
        if (status == null) {
            status = ReportStatus.ACTIVE;
        }
    }
}