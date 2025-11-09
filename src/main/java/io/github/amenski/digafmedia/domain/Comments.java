package io.github.amenski.digafmedia.domain;

import java.util.List;

public record Comments(List<Comment> result, Pagination pagination) {
    
    public Comments(List<Comment> result) {
        this(result, null);
    }
    
    public static Comments of(List<Comment> result, int page, int size, long totalElements) {
        Pagination pagination = Pagination.of(page, size, totalElements);
        return new Comments(result, pagination);
    }
}