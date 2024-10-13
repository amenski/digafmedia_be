package io.github.amenski.digafmedia.usecase;

import io.github.amenski.digafmedia.domain.Items;
import io.github.amenski.digafmedia.domain.repository.ItemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class GetAllItemsUseCase {

    private final ItemRepository itemRepository;

    public GetAllItemsUseCase(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Items invoke(String product) {
        if (StringUtils.isBlank(product)) {
            return new Items(itemRepository.findAll());
        }
        return new Items(itemRepository.findAllByProduct(product));
    }
}
