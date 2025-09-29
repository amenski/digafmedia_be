package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.Item;
import io.github.amenski.digafmedia.usecase.GetFreeServiceTextUseCase;
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

    private final GetFreeServiceTextUseCase getFreeServiceTextUseCase;

    public GenericController(GetFreeServiceTextUseCase getFreeServiceTextUseCase) {
        this.getFreeServiceTextUseCase = getFreeServiceTextUseCase;
    }

  @GetMapping("/free-service-text")
    ResponseEntity<Item> getAllItems() {
        Item result = null;
        try {
            result = getFreeServiceTextUseCase.invoke();
        } catch (Exception ex) {
            log.error("Error getting free service text.", ex);
        }
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

}
