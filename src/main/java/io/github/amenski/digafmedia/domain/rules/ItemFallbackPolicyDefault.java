package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.Item;

public final class ItemFallbackPolicyDefault implements ItemFallbackPolicy {
    @Override
    public Item fallbackForProduct(String productRoute) {
        // For now we return the same default regardless of route. Can be
        // extended later to be route-specific.
        return ItemDefaults.freeServiceFallback();
    }
}

