package io.github.amenski.digafmedia.domain.freeservice;

import io.github.amenski.digafmedia.domain.Pagination;
import java.util.List;

public record FreeServices(List<FreeService> services, Pagination pagination) {

    public FreeServices(List<FreeService> services) {
        this(services, null);
    }

    public static FreeServices of(List<FreeService> services, int page, int size, long totalElements) {
        Pagination pagination = Pagination.of(page, size, totalElements);
        return new FreeServices(services, pagination);
    }
}
