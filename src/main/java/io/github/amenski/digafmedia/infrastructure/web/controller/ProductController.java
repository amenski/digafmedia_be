package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.Items;
import io.github.amenski.digafmedia.usecase.GetAllItemsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/products/{product}")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final GetAllItemsUseCase getAllItemsUseCase;

    public ProductController(GetAllItemsUseCase getAllItemsUseCase) {
        this.getAllItemsUseCase = getAllItemsUseCase;
    }

    @GetMapping("/items")
    ResponseEntity<Items> getAllItems(@PathVariable String product) {
        try {
            Items result = getAllItemsUseCase.invoke(product);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error("Error getting items for product: {}", product, ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
