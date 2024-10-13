package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.Product;
import io.github.amenski.digafmedia.domain.repository.ProductRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring will get crazy if this class is renamed to ProductJpaRepositoryImpl, circular dependency
 */
@Repository
public class ProductDbRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductDbRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product findById(Long id) {
        return toDomain(productJpaRepository.findByIdOrThrow(id));
    }

    @Override
    public Product save(ProductEntity product) {
        return toDomain(productJpaRepository.save(product));
    }

    @Override
    public void delete(ProductEntity product) {
        productJpaRepository.delete(product);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private Product toDomain(ProductEntity productEntity) {
        return new Product(productEntity.getRouteName(), productEntity.getName(), productEntity.getImage());
    }
}
