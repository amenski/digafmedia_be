package io.github.amenski.digafmedia.domain.freeservice;

import java.util.List;

public record FreeServices(List<FreeService> services) {

    public static FreeServices of(List<FreeService> services) {
        return new FreeServices(services);
    }
}
