package io.github.amenski.digafmedia.domain.tikoma;

import io.github.amenski.digafmedia.domain.Pagination;
import java.util.List;

public record TikomaAlerts(List<TikomaAlert> alerts, Pagination pagination) {

    public TikomaAlerts(List<TikomaAlert> alerts) {
        this(alerts, null);
    }

    public static TikomaAlerts of(List<TikomaAlert> alerts, int page, int size, long totalElements) {
        Pagination pagination = Pagination.of(page, size, totalElements);
        return new TikomaAlerts(alerts, pagination);
    }
}
