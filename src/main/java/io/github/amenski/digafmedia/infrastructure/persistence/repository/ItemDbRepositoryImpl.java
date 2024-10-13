package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.Item;
import io.github.amenski.digafmedia.domain.repository.ItemRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.ItemEntity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.EthiopicChronology;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class ItemDbRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    public ItemDbRepositoryImpl(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    @Override
    public List<Item> findAllByProduct(String productName) {
        return itemJpaRepository.findAllByProductName(productName).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Item> findAll() {
        return itemJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private Item toDomain(ItemEntity itemEntity) { // do we need productId?
        OffsetDateTime publishedOn = itemEntity.getPublishedOn();
        DateTime dateTime = new DateTime(
                publishedOn.toInstant().toEpochMilli(),
                DateTimeZone.forID(publishedOn.getOffset().getId())
        );
        DateTime ethiopicDateTime = dateTime.withChronology(EthiopicChronology.getInstance());
        LocalDate date = LocalDate.of(ethiopicDateTime.getYear(), ethiopicDateTime.getMonthOfYear(), ethiopicDateTime.getDayOfMonth());

        return new Item(itemEntity.getTitle(), itemEntity.getContact(), itemEntity.getDescription(), itemEntity.getImages(), date);
    }

}
