package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    default ProductEntity findByIdOrThrow(long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("Product not found."));
    }
}
