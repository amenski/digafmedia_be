package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> findAllByProduct(String productName);

    List<Item> findAll();
}
