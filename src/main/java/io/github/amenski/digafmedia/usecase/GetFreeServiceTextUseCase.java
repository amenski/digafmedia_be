package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Item;
import io.github.amenski.digafmedia.usecase.port.ItemRepository;
import io.github.amenski.digafmedia.domain.rules.ItemFallbackPolicy;

import java.util.List;

public class GetFreeServiceTextUseCase {

    private final ItemRepository itemRepository;
    private final ItemFallbackPolicy itemFallbackPolicy;

    public GetFreeServiceTextUseCase(ItemRepository itemRepository, ItemFallbackPolicy itemFallbackPolicy) {
        this.itemRepository = itemRepository;
        this.itemFallbackPolicy = itemFallbackPolicy;
    }

    public Item invoke() {
        List<Item> items = itemRepository.findAllByProduct("free-service");
        if (items.isEmpty()) {
            return itemFallbackPolicy.fallbackForProduct("free-service");
        }
        return items.get(0);
    }
}
