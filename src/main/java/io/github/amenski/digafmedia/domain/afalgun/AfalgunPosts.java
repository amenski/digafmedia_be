package io.github.amenski.digafmedia.domain.afalgun;

import io.github.amenski.digafmedia.domain.Pagination;
import java.util.List;

public record AfalgunPosts(List<AfalgunPost> posts, Pagination pagination) {
    
    public AfalgunPosts(List<AfalgunPost> posts) {
        this(posts, null);
    }
    
    public static AfalgunPosts of(List<AfalgunPost> posts, int page, int size, long totalElements) {
        Pagination pagination = Pagination.of(page, size, totalElements);
        return new AfalgunPosts(posts, pagination);
    }
}