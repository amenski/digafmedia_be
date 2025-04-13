package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class ImageEntity extends BaseEntity {

  @Column(name = "file_path", nullable = false)
  private String filePath;

  @Column(name = "alt_text")
  private String altText;

  @Column(name = "display_order")
  private Integer displayOrder = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private ItemEntity item;

  // Getters and setters
  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getAltText() {
    return altText;
  }

  public void setAltText(String altText) {
    this.altText = altText;
  }

  public Integer getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    // Only allow association with either product or item
    if (product != null) {
      this.product = product;
      this.item = null;
    } else {
      this.product = null;
    }
  }

  public ItemEntity getItem() {
    return item;
  }

  public void setItem(ItemEntity item) {
    // Only allow association with either product or item
    if (item != null) {
      this.item = item;
      this.product = null;
    } else {
      this.item = null;
    }
  }

  public boolean belongsToProduct() {
    return product != null;
  }

  public boolean belongsToItem() {
    return item != null;
  }
}