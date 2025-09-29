package io.github.amenski.digafmedia.usecase.port;

import io.github.amenski.digafmedia.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(Long id);

    List<Product> findAll();
}

