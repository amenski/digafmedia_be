package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.Product;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductRepository {
    Product findById(Long id);

    Product save(ProductEntity product);

    void delete(ProductEntity product);

    List<Product> findAll();
}
