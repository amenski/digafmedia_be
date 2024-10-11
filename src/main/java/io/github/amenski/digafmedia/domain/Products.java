package io.github.amenski.digafmedia.domain;

import java.util.List;

public class Products {

    private final List<Product> items;

    public Products(List<Product> items) {
        this.items = items;
    }

    public List<Product> getProducts() {
        return items;
    }
}
