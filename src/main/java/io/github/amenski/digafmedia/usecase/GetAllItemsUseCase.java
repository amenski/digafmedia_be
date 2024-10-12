package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Products;
import io.github.amenski.digafmedia.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAllItemsUseCase {

    private final ProductRepository productRepository;

    public GetAllItemsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Products invoke() {
        return new Products(productRepository.findAll());
    }
}
