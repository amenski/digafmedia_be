package io.github.amenski.digafmedia.infrastructure.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "Pagination request parameters")
public class PageableRequest {
    @Min(value = 0, message = "Page must be greater than or equal to 0")
    private int page = 0;

    @Min(value = 1, message = "Size must be greater than or equal to 1")
    @Max(value = 100, message = "Size must be less than or equal to 100")
    private int size = 20;

    private String sortBy;
    private String sortDirection = "DESC";

    public PageableRequest() {
    }

    @Schema(description = "0-based page number", example = "0")
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Schema(description = "Number of items per page", example = "20")
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Schema(description = "Field to sort by")
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Schema(description = "Sort direction (ASC or DESC)", example = "DESC")
    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}