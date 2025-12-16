package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search request model for fundraising campaigns")
public class FundraisingCampaignSearchRequest {

    @Schema(description = "Search query text", example = "school")
    private String query;

    @Schema(description = "Filter by campaign status", example = "ACTIVE")
    private CampaignStatus status;

    @Schema(description = "Page number (0-based)", example = "0")
    private int page = 0;

    @Schema(description = "Page size", example = "10")
    private int size = 10;

    @Schema(description = "Sort field", example = "createdAt")
    private String sortBy = "createdAt";

    @Schema(description = "Sort direction", example = "DESC")
    private String sortDirection = "DESC";

    // Constructors
    public FundraisingCampaignSearchRequest() {
    }

    // Getters and setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
