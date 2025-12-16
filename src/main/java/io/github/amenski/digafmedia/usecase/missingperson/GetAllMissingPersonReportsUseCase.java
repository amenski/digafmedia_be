package io.github.amenski.digafmedia.usecase.missingperson;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.github.amenski.digafmedia.domain.missingperson.SearchMissingPersonReportsCommand;
import io.github.amenski.digafmedia.domain.repository.MissingPersonReportRepository;

import java.util.Objects;

public class GetAllMissingPersonReportsUseCase {

    private final MissingPersonReportRepository missingPersonReportRepository;

    public GetAllMissingPersonReportsUseCase(MissingPersonReportRepository missingPersonReportRepository) {
        this.missingPersonReportRepository = missingPersonReportRepository;
    }

    public PagedResult<MissingPersonReport> invoke(SearchMissingPersonReportsCommand command) {
        // Sanitize inputs: trim strings, treat empty strings as null
        String query = sanitizeQuery(command.query());
        String location = sanitizeLocation(command.location());
        ReportStatus status = command.status();
        int zeroBasedPage = command.page(); // page is zero‑based per command validation
        int size = command.size();

        // Convert zero‑based page to 1‑based for repository methods (which expect 1‑based)
        int pageForRepository = zeroBasedPage + 1;

        // Determine which repository method to call based on provided filters
        if (status == null && query == null && location == null) {
            // No filters
            var reports = missingPersonReportRepository.findAllPaginated(pageForRepository, size);
            var totalElements = missingPersonReportRepository.count();
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else if (status != null && query == null && location == null) {
            // Status only
            var reports = missingPersonReportRepository.findByStatusPaginated(status, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByStatus(status);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else if (status == null && query != null && location == null) {
            // Query only
            var reports = missingPersonReportRepository.searchByQuery(query, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByQuery(query);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else if (status == null && query == null && location != null) {
            // Location only
            var reports = missingPersonReportRepository.searchByLocation(location, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByLocation(location);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else if (status != null && query != null && location == null) {
            // Status + Query
            var reports = missingPersonReportRepository.searchByQueryAndStatus(query, status, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByQueryAndStatus(query, status);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else if (status != null && query == null && location != null) {
            // Status + Location
            var reports = missingPersonReportRepository.searchByStatusAndLocation(status, location, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByStatusAndLocation(status, location);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else if (status == null && query != null && location != null) {
            // Query + Location
            var reports = missingPersonReportRepository.searchByQueryAndLocation(query, location, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByQueryAndLocation(query, location);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        } else {
            // All three filters
            var reports = missingPersonReportRepository.searchByQueryStatusAndLocation(query, status, location, pageForRepository, size);
            var totalElements = missingPersonReportRepository.countByQueryStatusAndLocation(query, status, location);
            return new PagedResult<>(reports, totalElements, zeroBasedPage, size);
        }
    }

    private String sanitizeQuery(String query) {
        if (query == null) return null;
        String trimmed = query.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String sanitizeLocation(String location) {
        if (location == null) return null;
        String trimmed = location.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}