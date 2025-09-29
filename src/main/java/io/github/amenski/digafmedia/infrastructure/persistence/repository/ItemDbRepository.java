package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.Item;
import io.github.amenski.digafmedia.usecase.port.ItemRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.ImageEntity;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.ItemEntity;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.EthiopicChronology;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDbRepository implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    public ItemDbRepository(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    @Override
    public List<Item> findAllByProduct(String route) {
        return itemJpaRepository.findAllByProductRouteName(route).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Item> findAll() {
        return itemJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private Item toDomain(ItemEntity itemEntity) {
        OffsetDateTime publishedOn = itemEntity.getPublishedOn();
        DateTime dateTime = new DateTime(
                publishedOn.toInstant().toEpochMilli(),
                DateTimeZone.forID(publishedOn.getOffset().getId())
        );
        DateTime ethiopicDateTime = dateTime.withChronology(EthiopicChronology.getInstance());
        LocalDate date = LocalDate.of(ethiopicDateTime.getYear(), ethiopicDateTime.getMonthOfYear(), ethiopicDateTime.getDayOfMonth());

        List<String> images = itemEntity.getImages().stream()
                .sorted(Comparator.comparing(ImageEntity::getDisplayOrder))
                .map(ImageEntity::getFilePath)
                .toList();

        return new Item(itemEntity.getTitle(), itemEntity.getContact(), itemEntity.getDescription(), images, date);
    }

}
