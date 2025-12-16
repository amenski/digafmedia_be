package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Missing person report search request")
public class MissingPersonReportSearchRequest extends PageableRequest {

    @Size(max = 100, message = "Query must be less than 100 characters", groups = ValidationGroups.Search.class)
    private String query;

    private ReportStatus status;

    @Size(max = 100, message = "Location must be less than 100 characters", groups = ValidationGroups.Search.class)
    private String location;

    private Map<String, Object> filters;

    public MissingPersonReportSearchRequest() {
    }

    @Schema(description = "Full-text search query")
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Schema(description = "Filter by report status")
    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    @Schema(description = "Filter by location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Schema(description = "Dynamic filtering criteria")
    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }
}