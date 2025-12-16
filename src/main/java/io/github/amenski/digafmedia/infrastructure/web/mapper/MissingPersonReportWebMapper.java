package io.github.amenski.digafmedia.infrastructure.web.mapper;

import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.github.amenski.digafmedia.domain.missingperson.CreateMissingPersonReportCommand;
import io.github.amenski.digafmedia.domain.missingperson.UpdateMissingPersonReportCommand;
import io.github.amenski.digafmedia.domain.missingperson.SearchMissingPersonReportsCommand;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateMissingPersonReportRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.UpdateMissingPersonReportRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.MissingPersonReportSearchRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.MissingPersonReportResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.domain.PagedResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MissingPersonReportWebMapper {

    public CreateMissingPersonReportCommand create(CreateMissingPersonReportRequest request) {
        return new CreateMissingPersonReportCommand(
                request.getMissingPersonName(),
                request.getAge(),
                request.getLastSeenLocation(),
                request.getContactName(),
                request.getContactPhone(),
                request.getContactEmail(),
                request.getDescription(),
                request.getStatus()
        );
    }

    public UpdateMissingPersonReportCommand update(UpdateMissingPersonReportRequest request) {
        return new UpdateMissingPersonReportCommand(
                request.getStatus()
        );
    }

    public MissingPersonReportResponse toResponse(MissingPersonReport missingPersonReport) {
        return MissingPersonReportResponse.fromMissingPersonReport(missingPersonReport);
    }

    public PaginatedResponse<MissingPersonReportResponse> toPaginatedResponse(PagedResult<MissingPersonReport> pagedResult) {
        List<MissingPersonReportResponse> responses = pagedResult.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                responses,
                pagedResult.getPage(),
                pagedResult.getSize(),
                pagedResult.getTotalElements(),
                pagedResult.getTotalPages()
        );
    }

    public SearchMissingPersonReportsCommand from(MissingPersonReportSearchRequest searchRequest) {
        return new SearchMissingPersonReportsCommand(
                searchRequest.getQuery(),
                searchRequest.getStatus(),
                searchRequest.getLocation(),
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );
    }
}