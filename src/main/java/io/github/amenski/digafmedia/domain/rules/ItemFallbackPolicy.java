package io.github.amenski.digafmedia.domain.rules;

import io.github.amenski.digafmedia.domain.Item;

public interface ItemFallbackPolicy {
    Item fallbackForProduct(String productRoute);
}

