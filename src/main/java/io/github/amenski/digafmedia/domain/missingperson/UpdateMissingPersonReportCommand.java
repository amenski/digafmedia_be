package io.github.amenski.digafmedia.domain.missingperson;

public record UpdateMissingPersonReportCommand(
    ReportStatus status
) {}