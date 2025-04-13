package io.github.amenski.digafmedia.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
public class ItemEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "published_on", nullable = false)
    private OffsetDateTime publishedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ImageEntity> images = new ArrayList<>();

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(OffsetDateTime publishedOn) {
        this.publishedOn = publishedOn;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    // Helper methods for managing the bidirectional relationship
    public void addImage(ImageEntity image) {
        images.add(image);
        image.setItem(this);
    }

    public void removeImage(ImageEntity image) {
        images.remove(image);
        image.setItem(null);
    }
}