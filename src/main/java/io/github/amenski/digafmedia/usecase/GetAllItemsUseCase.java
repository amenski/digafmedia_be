package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Products;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetAllItemsUseCase {

    public Products invoke() {
        return new Products(new ArrayList<>());
    }
}
