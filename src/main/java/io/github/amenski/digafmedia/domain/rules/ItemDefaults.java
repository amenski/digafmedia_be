package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.Item;

import java.time.LocalDate;
import java.util.List;

public final class ItemDefaults {
    private ItemDefaults() {}

    public static Item freeServiceFallback() {
        return new Item("ignore", "ignore", "No data.", List.of(), LocalDate.now());
    }
}

