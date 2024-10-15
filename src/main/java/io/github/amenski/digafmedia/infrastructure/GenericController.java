package io.github.amenski.digafmedia.infrastructure;

import io.github.amenski.digafmedia.domain.Item;
import io.github.amenski.digafmedia.domain.repository.ItemRepository;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/generic/")
public class GenericController {

    private static final Logger log = LoggerFactory.getLogger(GenericController.class);

    private final ItemRepository itemRepository;

  public GenericController(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @GetMapping("/free-service-text")
    ResponseEntity<Item> getAllItems() {
        Item result = null;
        try {
            List<Item> items = itemRepository.findAllByProduct("free-service");
            if (items.isEmpty()) {
                result = new Item("ignore", "ignore", "No data.", List.of(), LocalDate.now());
            } else {
                result = items.get(0);
            }
        } catch (Exception ex) {
            log.error("Error getting free service text.", ex);
        }
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

}
