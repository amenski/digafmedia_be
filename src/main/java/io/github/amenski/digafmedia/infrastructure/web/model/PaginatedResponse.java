package io.github.amenski.digafmedia.infrastructure.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated response")
public class PaginatedResponse<T> {
    private int page;
    private int size;
    private long total;
    private int totalPages;
    private List<T> items;

    public PaginatedResponse() {
    }

    public PaginatedResponse(List<T> items, int page, int size, long total, int totalPages) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
    }

    @Schema(description = "0-based page number")
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Schema(description = "Page size")
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Schema(description = "Total elements")
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Schema(description = "Total pages")
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Schema(description = "Page content")
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}