package io.github.amenski.digafmedia.infrastructure;

import io.github.amenski.digafmedia.domain.Products;
import io.github.amenski.digafmedia.usecase.GetAllItemsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("v1/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final GetAllItemsUseCase getAllItemsUseCase;

    public ProductController(GetAllItemsUseCase getAllItemsUseCase) {
        this.getAllItemsUseCase = getAllItemsUseCase;
    }

    @GetMapping("/items")
    ResponseEntity<Products> getAllItems() {
        Products products = new Products(new ArrayList<>());
        try {
            products = getAllItemsUseCase.invoke();
        } catch (Exception ex) {
            log.error("Error getting all items.", ex);
        }
        return new ResponseEntity<>(products, HttpStatusCode.valueOf(200));
    }

}
