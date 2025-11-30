package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.image.PostType;
import jakarta.persistence.*;

@Entity
@Table(name = "post_image")
public class PostImageEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type", nullable = false)
    private PostType postType;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "file_path", length = 500, nullable = false)
    private String filePath;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_by")
    private Long createdBy;

    // Getters and setters
    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
