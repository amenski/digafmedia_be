package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {

    @Column(name = "route_name", nullable = false)
    private String routeName;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ImageEntity> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ItemEntity> items = new ArrayList<>();

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    // Helper methods for managing the bidirectional relationship with images
    public void addImage(ImageEntity image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(ImageEntity image) {
        images.remove(image);
        image.setProduct(null);
    }

    // Helper methods for managing the bidirectional relationship with items
    public void addItem(ItemEntity item) {
        items.add(item);
        item.setProduct(this);
    }

    public void removeItem(ItemEntity item) {
        items.remove(item);
        item.setProduct(null);
    }
}