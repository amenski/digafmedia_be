package io.github.amenski.digafmedia.domain.missingperson;

public record SearchMissingPersonReportsCommand(
    String query,
    ReportStatus status,
    String location,
    int page,
    int size,
    String sortBy,
    String sortDirection
) {
    public SearchMissingPersonReportsCommand {
        if (page < 0) page = 0;
        if (size < 1) size = 20;
        if (size > 100) size = 100;
    }
}